package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRatePlnApi;

@Service
public class ApiServicePln implements ApiService {

    private final ExchangeRatePlnMapper plnMapper;

    @Autowired
    public ApiServicePln(ExchangeRatePlnMapper plnMapper) {
        this.plnMapper = plnMapper;
    }

    @Override
    public ExchangeRate getRate(String base, String target, String date) {
        String request = "http://api.nbp.pl/api/exchangerates/rates/a/" + target + "/" + date + "/";
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRatePlnApi rate = restTemplate.getForObject(request, ExchangeRatePlnApi.class);

        assert rate != null;
        return plnMapper.map(rate);
    }
}
