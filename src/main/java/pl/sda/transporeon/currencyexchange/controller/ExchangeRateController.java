package pl.sda.transporeon.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.transporeon.currencyexchange.model.*;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;
import pl.sda.transporeon.currencyexchange.repository.ExchangeStatisticRepository;
import pl.sda.transporeon.currencyexchange.service.ExchangeRateService;
import pl.sda.transporeon.currencyexchange.service.ExchangeStatisticService;

import java.net.URI;

@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository repository;
    private final ExchangeStatisticRepository statisticRepository;
    private final ExchangeStatisticService statisticService;
    public static final String GOLD_CODE = "XAU";
    public static final String PLN_CODE = "PLN";

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateRepository repository, ExchangeStatisticRepository statisticRepository, ExchangeStatisticService statisticService) {
        this.exchangeRateService = exchangeRateService;
        this.repository = repository;
        this.statisticRepository = statisticRepository;
        this.statisticService = statisticService;
    }

    @PostMapping("/exchange")
    ResponseEntity<ExchangeRateDTO> createRate(@RequestBody ExchangePost toCreate) {

        statisticService.saveStatisticModelToBd(new ExchangeStatisticModel(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(), toCreate.getExchangeDate()));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(), toCreate.getExchangeDate());
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/{base}/{target}/{date}")
    ResponseEntity<ExchangeRateDTO> createRateUrl(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {

        statisticService.saveStatisticModelToBd(new ExchangeStatisticModel(base,target,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base.toUpperCase(), target.toUpperCase(), date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }


    @GetMapping("/exchange/gold/{date}")
    ResponseEntity<ExchangeRateDTO> createGoldUrl(
            @PathVariable String date) {

        statisticService.saveStatisticModelToBd(new ExchangeStatisticModel(PLN_CODE, GOLD_CODE,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(GOLD_CODE, PLN_CODE, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange")
    ResponseEntity<Iterable<ExchangeRate>> readAllExchangeRate() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping(value = "/exchange/remove")
    public ResponseEntity<?> deleteAll() {
        exchangeRateService.remove();

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/statistic/{base}/{target}/{date}")
    ResponseEntity<ExchangeStatisticDTO> statisticValueUrl(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {

        ExchangeStatisticDTO result = statisticService.calculateStatistic(base, target, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/statistic")
    ResponseEntity<Iterable<ExchangeStatisticModel>> readAllStatistic() {
        return ResponseEntity.ok(statisticRepository.findAll());
    }



    @DeleteMapping(value = "/statistic/remove")
    public ResponseEntity<?> cleanStatisticBD() {

        statisticService.cleanStatisticDb();
        return ResponseEntity.noContent().build();

    }


}
