package pl.sda.transporeon.currencyexchange.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository repository;

    @Test
    void findDistinctByBaseCurrencyAndTargetCurrencyAndExchangeDate() {

        repository.
                save(new ExchangeRate(1, "PLN", "USD", 4.25, LocalDate.now()));
        ExchangeRate exchangeRate = repository.
                save(new ExchangeRate(2, "PLN", "GBP", 5.75, LocalDate.now()));
        repository.
                save(new ExchangeRate(3, "EUR", "CHF", 1.15, LocalDate.now()));

        assertEquals(exchangeRate, repository.findDistinctByBaseCurrencyAndTargetCurrencyAndExchangeDate("PLN", "GBP", LocalDate.now()));
    }
}