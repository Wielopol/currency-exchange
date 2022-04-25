package pl.sda.transporeon.currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExchangeRateRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void httpGet_returnsGivenHistoryGold() throws Exception {
        //give
        LocalDate date = repository.
                save(new ExchangeRate(1, "PLN", "USD", 3.75, LocalDate.now())).getExchangeDate();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/history/gold/" + date))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void httpGet_currencyExchangeToDay() throws Exception {
        //give

        LocalDate date = repository.
                save(new ExchangeRate(1, "PLN", "GBP", 3.75, LocalDate.now())).getExchangeDate();

        //when + then

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/history/PLN/GBP/" + date))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("{\"baseCurrency\":\"PLN\",\"targetCurrency\":\"GBP\",\"rate\":3.75,\"exchangeDate\":\"2022-04-25\"}"));

    }
    @Test
    void http_addExchangeRate_toDB() throws Exception {
        //give
        var exchangeRate = new ExchangeRate(15,"PLN", "GBP", 55.6, LocalDate.now());

        //When + then
        mockMvc.perform(post("/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exchangeRate)))
                .andDo(print())
                .andExpect(status().isCreated());

    }



}