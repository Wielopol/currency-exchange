package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.sda.transporeon.currencyexchange.controller.exception.RateProcessingException;
import pl.sda.transporeon.currencyexchange.model.*;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import java.time.LocalDate;
import java.util.List;

import static pl.sda.transporeon.currencyexchange.controller.ExchangeRateController.gold;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplateConfig restTemplate;
    private final ExchangeRateMapper mapper;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,  RestTemplateConfig restTemplate, ExchangeRateMapper mapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    public ExchangeRateDTO getExchangeDataToView(String base, String target, String date) {
        ExchangeRate rate;
        String request;
        boolean recordExists = doesRecordExists(base, target, mapper.stringDateToLocalDate(date));

        try {
            if (!recordExists) {
                if(base.equals(gold)){
                    request = "http://api.nbp.pl/api/cenyzlota/" + date + "/";
                    rate = mapper.mapGold(getGoldRate(request));
                } else {
                    request = "https://api.exchangerate.host/" + date + "?base=" + base + "&symbols=" + target;
                    rate = mapper.mapCurrency(getCurrencyRate(request), target);
                }
                exchangeRateRepository.save(rate);
            } else {
                List<ExchangeRate> records = findRecord(base, target, mapper.stringDateToLocalDate(date));
                rate = records.get(0);
            }
        } catch (HttpClientErrorException | NullPointerException e) {
            throw new RateProcessingException("Cannot get currency data");
        }

        return mapper.mapToDto(rate);
    }

    public void remove(){
        exchangeRateRepository.deleteAll(exchangeRateRepository.findAll());
    }

    public boolean doesRecordExists(String baseCurrency, String targetCurrency, LocalDate exchangeDate) {
        return exchangeRateRepository.existsByBaseCurrencyAndTargetCurrencyAndExchangeDate(baseCurrency, targetCurrency, exchangeDate);
    }

    public List<ExchangeRate> findRecord(String baseCurrency, String targetCurrency, LocalDate exchangeDate) {
        return exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyAndExchangeDate(baseCurrency, targetCurrency, exchangeDate);
    }

    public ExchangeRateGoldApi getGoldRate(String request) throws HttpClientErrorException {
        ExchangeRateGoldApi[] rawRates = restTemplate.restTemplate().getForObject(request, ExchangeRateGoldApi[].class);
        if (rawRates != null) {
            return rawRates[0];
        }
        return new ExchangeRateGoldApi();
    }

    public ExchangeRateCurrencyApi getCurrencyRate(String request) throws HttpClientErrorException {
        return restTemplate.restTemplate().getForObject(request, ExchangeRateCurrencyApi.class);
    }
}
