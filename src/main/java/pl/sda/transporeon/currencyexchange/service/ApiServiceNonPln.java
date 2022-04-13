package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateNonPlnApi;

@Service
public class ApiServiceNonPln implements ApiService {

    private final ExchangeRateNonPlnMapper nonPlnMapper;

    @Autowired
    public ApiServiceNonPln(ExchangeRateNonPlnMapper nonPlnMapper) {
        this.nonPlnMapper = nonPlnMapper;
    }

    @Override
    public ExchangeRate getRate(String base, String target, String date) {
        String request = "https://api.exchangerate.host/" + date + "?base=" + base + "&symbols=" + target;
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateNonPlnApi rate = restTemplate.getForObject(request, ExchangeRateNonPlnApi.class);

        assert rate != null;
        return nonPlnMapper.map(rate, target);
    }
}
