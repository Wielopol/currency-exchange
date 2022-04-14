package pl.sda.transporeon.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeRateDTO {

    private String baseCurrency;
    private String targetCurrency;
    private double rate;
    private LocalDate exchangeDate;


}
