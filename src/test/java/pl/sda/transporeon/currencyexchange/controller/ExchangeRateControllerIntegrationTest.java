package pl.sda.transporeon.currencyexchange.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExchangeRateRepository repository;

    @Test
    void httpGet_returnsGivenHistoryGold() throws Exception {
        //give
        LocalDate date = repository.
                save(new ExchangeRate(1, "PLN", "USD", 3.75, LocalDate.now())).getExchangeDate();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/history/gold/"+ date))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }


}