package pl.sda.transporeon.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;
import pl.sda.transporeon.currencyexchange.service.ExchangeRateService;

import java.net.URI;

@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository repository;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateRepository repository) {
        this.exchangeRateService = exchangeRateService;
        this.repository = repository;
    }

//    @PostMapping("/exchange")
//    ResponseEntity<ExchangeRateDTO> createRate(@RequestBody ExchangeRate toCreate){
//        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(),toCreate.getExchangeDate());
//        return ResponseEntity.created(URI.create("/"+ result.getBaseCurrency())).body(result);
//    }

        @GetMapping("/exchange/{base}/{target}/{date}")
    ResponseEntity<ExchangeRateDTO> createRateUrl(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base, target, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange")
    ResponseEntity<Iterable<ExchangeRate>> readAll(){
        return ResponseEntity.ok(repository.findAll());
    }





}
