package pl.sda.transporeon.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExchangeRateGoldApi {
    @JsonProperty("data")
    private String date;
    @JsonProperty("cena")
    private double rate;
}
