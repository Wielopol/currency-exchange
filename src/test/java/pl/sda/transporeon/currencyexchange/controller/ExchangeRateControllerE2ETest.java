package pl.sda.transporeon.currencyexchange.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticModel;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;
import pl.sda.transporeon.currencyexchange.repository.ExchangeStatisticRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeRateControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExchangeRateRepository repository;

    @Autowired
    ExchangeStatisticRepository statisticRepository;


    @Test
    void httpGet_returnAllExchangeRateTest(){
        // given
        repository.save(new ExchangeRate(5, "PLN", "USD", 3.14, LocalDate.now()));
        repository.save(new ExchangeRate(6, "USD", "GBP", 5.55, LocalDate.now()));

        //when
        ExchangeRate[] result = restTemplate
                .getForObject("http://localhost:" + port + "/exchange/all/currency", ExchangeRate[].class);
        //then
        assertThat(result).hasSize(2);
        //and

    }

    @Test
    void httpGet_returnAllExchangeStatisticTest(){
        // given
        statisticRepository.save(new ExchangeStatisticModel(16,"USA","GBP", String.valueOf(LocalDate.now())));
        statisticRepository.save(new ExchangeStatisticModel(17,"PLN", "USA", String.valueOf(LocalDate.now())));

        //when
        ExchangeStatisticModel[] result = restTemplate
                .getForObject("http://localhost:" + port + "/statistic/all/queries", ExchangeStatisticModel[].class);
        //then
        assertThat(result).hasSize(2);
    }


}
