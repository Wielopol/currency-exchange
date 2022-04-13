package pl.sda.transporeon.currencyexchange.service;

import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

public interface ApiService {
    public ExchangeRate getRate(String base, String target, String date);
}
