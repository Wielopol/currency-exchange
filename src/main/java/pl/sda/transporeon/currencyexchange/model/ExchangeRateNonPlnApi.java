package pl.sda.transporeon.currencyexchange.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExchangeRateNonPlnApi {
    private String base;
    private String date;
    private Map<String,Double> rates;
}
