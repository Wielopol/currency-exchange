package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Component;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;

@Component
public class MapToDTO {

    public ExchangeRateDTO mapToDto(ExchangeRate exchangeRate){

        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        rateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        rateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        rateDTO.setRate(exchangeRate.getRate());
        rateDTO.setExchangeDate(exchangeRate.getExchangeDate());

        return rateDTO;
    }
}
