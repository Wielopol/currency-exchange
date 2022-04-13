package pl.sda.transporeon.currencyexchange.service;

import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

@Service
public class ExchangeRateApi {

    public ExchangeRate getExchangeRate(String base,
                                        String target,
                                        Date data){

        data = Date.valueOf(LocalDate.now());
        double casch = 5.5;


        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(base);
        exchangeRate.setTargetCurrency(target);
        exchangeRate.setRate(casch);
        exchangeRate.setExchangeDate(data);

        return exchangeRate;
    }


}
