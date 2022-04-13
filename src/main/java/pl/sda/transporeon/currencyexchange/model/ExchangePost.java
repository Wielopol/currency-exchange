package pl.sda.transporeon.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangePost {

    private String baseCurrency;
    private String targetCurrency;
    private String exchangeDate;

}
