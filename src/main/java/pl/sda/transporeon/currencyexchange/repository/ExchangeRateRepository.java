package pl.sda.transporeon.currencyexchange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

import java.time.LocalDate;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Integer> {
    ExchangeRate findDistinctByBaseCurrencyAndTargetCurrencyAndExchangeDate(String baseCurrency, String targetCurrency, LocalDate exchangeDate);
}
