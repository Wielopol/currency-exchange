package pl.sda.transporeon.currencyexchange.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExchangeRateApi {
    private String code;
    private Rates[] rates;

    @Getter
    @ToString
    private class Rates {
        private String effectiveDate;
        private double mid;
    }
}
