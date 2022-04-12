package pl.sda.transporeon.currencyexchange.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Integer> {
}
