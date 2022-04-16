package pl.sda.transporeon.currencyexchange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticModel;

@Repository
public interface ExchangeStatisticRepository extends CrudRepository<ExchangeStatisticModel, Integer> {
}
