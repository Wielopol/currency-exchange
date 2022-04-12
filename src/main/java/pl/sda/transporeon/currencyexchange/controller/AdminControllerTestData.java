package pl.sda.transporeon.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class AdminControllerTestData {

    private final ExchangeRateRepository repository;

    @Autowired
    public AdminControllerTestData(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin/utils/addToDbTest")
    ResponseEntity<?> addTestData(){

        ExchangeRate rate1 = new ExchangeRate();
        rate1.setBaseCurrency("EURO");
        rate1.setTargetCurrency("PLN");
        rate1.setRate(34.5);
        rate1.setExchangeDate(Date.valueOf(LocalDate.now()));

        repository.save(rate1);

        return ResponseEntity.noContent().build();

    }




}
