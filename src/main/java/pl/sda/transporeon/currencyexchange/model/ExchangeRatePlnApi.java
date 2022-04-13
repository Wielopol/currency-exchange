package pl.sda.transporeon.currencyexchange.model;

import lombok.Getter;

@Getter
public class ExchangeRatePlnApi {
    private String code;
    private Rates[] rates;

    @Getter
    public static class Rates {
        private String effectiveDate;
        private double mid;
    }
}
