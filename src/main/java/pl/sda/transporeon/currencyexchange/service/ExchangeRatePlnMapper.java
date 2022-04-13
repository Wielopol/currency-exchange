package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Component;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRatePlnApi;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ExchangeRatePlnMapper {
    public ExchangeRate map(ExchangeRatePlnApi rate) {
        Date exchangeDate = null;
        try {
            java.util.Date dt = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(rate.getRates()[0].getEffectiveDate());
            exchangeDate = new java.sql.Date(dt.getTime());
        } catch (ParseException e) {
            System.err.println(e);
        }
        return new ExchangeRate(null, "PLN", rate.getCode(), rate.getRates()[0].getMid(), exchangeDate);
    }
}
