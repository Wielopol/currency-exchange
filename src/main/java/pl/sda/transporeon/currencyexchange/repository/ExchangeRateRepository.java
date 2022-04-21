package pl.sda.transporeon.currencyexchange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Integer> {
    boolean existsByBaseCurrencyAndTargetCurrencyAndExchangeDate(String baseCurrency, String targetCurrency, LocalDate exchangeDate);
    ExchangeRate findDistinctByBaseCurrencyAndTargetCurrencyAndExchangeDate(String baseCurrency, String targetCurrency, LocalDate exchangeDate);
}
