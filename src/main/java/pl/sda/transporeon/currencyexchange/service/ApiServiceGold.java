package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRatePlnApi;

@Service
public class ApiServiceGold implements ApiService {

    private final ExchangeRatePlnMapper plnMapper;

    @Autowired
    public ApiServiceGold(ExchangeRatePlnMapper plnMapper) {
        this.plnMapper = plnMapper;
    }

    @Override
    public ExchangeRate getRate(String base, String target, String date) {
        String request = "http://api.nbp.pl/api/cenyzlota/" + date + "/";
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRatePlnApi rate = restTemplate.getForObject(request, ExchangeRatePlnApi.class);

        assert rate != null;
        return plnMapper.map(rate);
    }
}
