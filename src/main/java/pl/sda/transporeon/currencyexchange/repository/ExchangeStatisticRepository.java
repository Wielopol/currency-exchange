package pl.sda.transporeon.currencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticModel;

@Repository
public interface ExchangeStatisticRepository extends JpaRepository<ExchangeStatisticModel, Integer> {
}
