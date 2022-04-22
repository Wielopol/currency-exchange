package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Component;
import pl.sda.transporeon.currencyexchange.controller.exception.RateProcessingException;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateCurrencyApi;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateGoldApi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static pl.sda.transporeon.currencyexchange.controller.ExchangeRateController.GOLD_CODE;
import static pl.sda.transporeon.currencyexchange.controller.ExchangeRateController.PLN_CODE;

@Component
public class ExchangeRateMapper {
    public ExchangeRate mapCurrency(ExchangeRateCurrencyApi rate, String base, String target) {
        if (rate.getRates().get(target) == null || !rate.getBase().equals(base)) {
            throw new RateProcessingException("Cannot get currency data");
        }
        return new ExchangeRate(null, rate.getBase(), target, rate.getRates().get(target), stringDateToLocalDate(rate.getDate()));
    }

    public ExchangeRate mapGold(ExchangeRateGoldApi rate) {
        return new ExchangeRate(null, GOLD_CODE, PLN_CODE, rate.getRate(), stringDateToLocalDate(rate.getDate()));
    }

    public LocalDate stringDateToLocalDate(String date) {
        if (date.equals("latest")) {
            return LocalDate.now(ZoneId.of("GMT"));
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public ExchangeRateDTO mapToDto(ExchangeRate exchangeRate){

        return new ExchangeRateDTO(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate(), exchangeRate.getExchangeDate());
    }


}
