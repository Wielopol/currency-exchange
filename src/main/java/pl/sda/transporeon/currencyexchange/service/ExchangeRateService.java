package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.sda.transporeon.currencyexchange.controller.exception.RateProcessingException;
import pl.sda.transporeon.currencyexchange.model.*;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import java.time.LocalDate;
import java.util.List;

import static pl.sda.transporeon.currencyexchange.controller.ExchangeRateController.GOLD_CODE;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private final ExchangeRateMapper mapper;
    @Value("${apiGold.url}")
    private String apiGoldUrl;
    @Value("${apiCurrency.url}")
    private String apiCurrencyUrl;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,  RestTemplateConfig restTemplate, ExchangeRateMapper mapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate.restTemplate();
        this.mapper = mapper;
    }


    public ExchangeRateDTO getExchangeDataToView(String base, String target, String date) {
        ExchangeRate rate;
        String request;
        boolean recordExists = doesRecordExists(base, target, mapper.stringDateToLocalDate(date));

        try {
            if (!recordExists) {
                if(base.equals(GOLD_CODE)){
                    request = apiGoldUrl + date;
                    rate = mapper.mapGold(getGoldRate(request));
                } else {
                    request = apiCurrencyUrl + date + "?base=" + base + "&symbols=" + target;
                    rate = mapper.mapCurrency(getCurrencyRate(request), base, target);
                }
                exchangeRateRepository.save(rate);
            } else {
                List<ExchangeRate> records = findRecord(base, target, mapper.stringDateToLocalDate(date));
                rate = records.get(0);
            }
        } catch (HttpClientErrorException e) {
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
        ExchangeRateGoldApi[] rawRates = restTemplate.getForObject(request, ExchangeRateGoldApi[].class);
        if (rawRates != null) {
            return rawRates[0];
        }
        return new ExchangeRateGoldApi();
    }

    public ExchangeRateCurrencyApi getCurrencyRate(String request) throws HttpClientErrorException {
        return restTemplate.getForObject(request, ExchangeRateCurrencyApi.class);
    }

    public List<ExchangeRate> findAll(){
       return (List<ExchangeRate>) exchangeRateRepository.findAll();
    }
}
