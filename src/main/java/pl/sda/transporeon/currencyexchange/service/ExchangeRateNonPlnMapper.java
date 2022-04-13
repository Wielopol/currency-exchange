package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Component;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateNonPlnApi;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ExchangeRateNonPlnMapper {
    public ExchangeRate map(ExchangeRateNonPlnApi rate, String target) {
        Date exchangeDate = null;
        try {
            java.util.Date dt = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(rate.getDate());
            exchangeDate = new java.sql.Date(dt.getTime());
        } catch (ParseException e) {
            System.err.println(e);
        }
        return new ExchangeRate(null, rate.getBase(), target, rate.getRates().get(target), exchangeDate);
    }
}
