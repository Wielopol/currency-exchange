package pl.sda.transporeon.currencyexchange.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.model.*;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import static pl.sda.transporeon.currencyexchange.controller.ExchangeRateController.gold;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final MapToDTO mapToDTO;
    private final RestTemplateConfig restTemplate;
    private final ExchangeRateMapper mapper;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, MapToDTO mapToDTO, RestTemplateConfig restTemplate, ExchangeRateMapper mapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.mapToDTO = mapToDTO;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    public ExchangeRateDTO getExchangeDataToView(String base, String target, String date) {
        ExchangeRate rate = null;
        if(base.equals(gold)){
            String request = "http://api.nbp.pl/api/cenyzlota/" + date + "/";
            ExchangeRateGoldApi[] rawRates = restTemplate.restTemplate().getForObject(request, ExchangeRateGoldApi[].class);
            if (rawRates != null) {
                rate = mapper.mapGold(rawRates[0]);
            }
        }else {
            String request = "https://api.exchangerate.host/" + date + "?base=" + base + "&symbols=" + target;
            ExchangeRateCurrencyApi rawRate = restTemplate.restTemplate().getForObject(request, ExchangeRateCurrencyApi.class);
            if (rawRate != null) {
                rate = mapper.mapCurrency(rawRate, target);
            }
        }
        exchangeRateRepository.save(rate);
        ExchangeRateDTO rateDTO = mapToDTO.mapToDto(rate);

        return rateDTO;
    }

    public void remove(){

        exchangeRateRepository.deleteAll(exchangeRateRepository.findAll());

    }
}
