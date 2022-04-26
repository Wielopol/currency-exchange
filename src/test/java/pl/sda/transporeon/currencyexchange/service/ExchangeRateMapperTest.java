package pl.sda.transporeon.currencyexchange.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateCurrencyApi;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateMapperTest {

    @Autowired
    private ExchangeRateMapper mapper;

    @Test
    void stringLatestDateToLocalDate() {
        assertEquals(LocalDate.now(ZoneId.of("GMT")), mapper.stringDateToLocalDate("latest"));
    }

    @Test
    void stringHistoryDateToLocalDate() {
        assertEquals(LocalDate.of(2022,4,12), mapper.stringDateToLocalDate("2022-04-12"));
    }
}