package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Component;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateCurrencyApi;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateGoldApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ExchangeRateMapper {
    public ExchangeRate mapCurrency(ExchangeRateCurrencyApi rate, String target) {
        return new ExchangeRate(null, rate.getBase(), target, rate.getRates().get(target), stringDateToLocalDate(rate.getDate()));
    }

    public ExchangeRate mapGold(ExchangeRateGoldApi rate) {
        return new ExchangeRate(null, "XAU", "PLN", rate.getRate(), stringDateToLocalDate(rate.getDate()));
    }

    public LocalDate stringDateToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
