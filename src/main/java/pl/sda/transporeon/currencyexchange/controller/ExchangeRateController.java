package pl.sda.transporeon.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;
import pl.sda.transporeon.currencyexchange.model.ExchangePost;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;
import pl.sda.transporeon.currencyexchange.service.ExchangeRateService;

import java.net.URI;

@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository repository;
    public static final String gold = "XAU";
    public static final String pln = "PLN";

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateRepository repository) {
        this.exchangeRateService = exchangeRateService;
        this.repository = repository;
    }

    @PostMapping("/exchange")
    ResponseEntity<ExchangeRateDTO> createRate(@RequestBody ExchangePost toCreate) {
        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(), toCreate.getExchangeDate());
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/{base}/{target}/{date}")
    ResponseEntity<ExchangeRateDTO> createRateUrl(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base.toUpperCase(), target.toUpperCase(), date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/gold/{date}")
    ResponseEntity<ExchangeRateDTO> createGoldUrl(
            @PathVariable String date) {

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(gold, pln, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange")
    ResponseEntity<Iterable<ExchangeRate>> readAll() {
        return ResponseEntity.ok(repository.findAll());
    }


    @DeleteMapping(value = "/exchange/remove")
    public ResponseEntity<?> deleteAll() {
        exchangeRateService.remove();

        return ResponseEntity.noContent().build();

    }


}
