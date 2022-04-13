package pl.sda.transporeon.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeRateDTO {

    private String baseCurrency;
    private String targetCurrency;
    private double rate;
    private Date exchangeDate;


}
