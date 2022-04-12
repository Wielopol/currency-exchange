package pl.sda.transporeon.currencyexchange.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rates_generator")
    @SequenceGenerator(name = "rates_generator", sequenceName = "rates_rate_id_seq", allocationSize = 1)
    private int id;
    private String baseCurrency;
    private String targetCurrency;
    private double rate;
    private Date exchangeDate;


}
