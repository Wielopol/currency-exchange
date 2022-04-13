package pl.sda.transporeon.currencyexchange.service;

import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;

public class MapToDTO {

    public ExchangeRateDTO mapToDto(ExchangeRate exchangeRate){

        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        rateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        rateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        rateDTO.setExchangeDate(exchangeRate.getExchangeDate());

        return rateDTO;
    }
}
