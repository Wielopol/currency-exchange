package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ApiServiceNonPln apiServiceNonPln;
    private final ApiServicePln apiServicePln;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, ApiServiceNonPln apiServiceNonPln, ApiServicePln apiServicePln) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.apiServiceNonPln = apiServiceNonPln;
        this.apiServicePln = apiServicePln;
    }
}
