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
import java.time.LocalDate;

@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository repository;
    private final ExchangeStatisticRepository statisticRepository;
    private final ExchangeStatisticService statisticService;
    public static final String gold = "XAU";
    public static final String pln = "PLN";

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateRepository repository, ExchangeStatisticRepository statisticRepository, ExchangeStatisticService statisticService) {
        this.exchangeRateService = exchangeRateService;
        this.repository = repository;
        this.statisticRepository = statisticRepository;
        this.statisticService = statisticService;
    }

    @PostMapping("/exchange")
    ResponseEntity<ExchangeRateDTO> createRate(@RequestBody ExchangePost toCreate) {

        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(), toCreate.getExchangeDate()));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(toCreate.getBaseCurrency(), toCreate.getTargetCurrency(), toCreate.getExchangeDate());
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }


    @GetMapping("/exchange/history/{base}/{target}/{date}")
    ResponseEntity<ExchangeRateDTO> createHistoryRateUrl(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {

        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(base,target,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base.toUpperCase(), target.toUpperCase(), date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }
    @GetMapping("/exchange/history/gold/{date}")
    ResponseEntity<ExchangeRateDTO> createHistoryGoldUrl(
            @PathVariable String date) {
        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(pln,gold,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(gold, pln, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/latest/{base}/{target}")
    ResponseEntity<ExchangeRateDTO> createTodayRateUrl(
            @PathVariable String base,
            @PathVariable String target) {

        String date = String.valueOf(LocalDate.now());

        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(base,target,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base.toUpperCase(), target.toUpperCase(), date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }


    @GetMapping("/exchange/latest/gold")
    ResponseEntity<ExchangeRateDTO> createTodayGoldUrl(
            ) {
        String date = String.valueOf(LocalDate.now());
        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(pln,gold,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(gold, pln, date);
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
