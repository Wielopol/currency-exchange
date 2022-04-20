package pl.sda.transporeon.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistic")
public class ExchangeStatisticModel {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistic_generator")
        @SequenceGenerator(name = "statistic_generator", sequenceName = "statistic_rate_id_seq", allocationSize = 1)
        private Integer id;
        private String baseCurrency;
        private String targetCurrency;
        private String exchangeDate;

    public ExchangeStatisticModel(String baseCurrency, String targetCurrency, String exchangeDate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeDate = exchangeDate;
    }




}
