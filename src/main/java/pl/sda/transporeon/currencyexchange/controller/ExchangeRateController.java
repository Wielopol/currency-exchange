package pl.sda.transporeon.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.transporeon.currencyexchange.model.*;
import pl.sda.transporeon.currencyexchange.service.ExchangeRateService;
import pl.sda.transporeon.currencyexchange.service.ExchangeStatisticService;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;

@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeStatisticService statisticService;
    public static final String GOLD_CODE = "XAU";
    public static final String PLN_CODE = "PLN";

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService,  ExchangeStatisticService statisticService) {
        this.exchangeRateService = exchangeRateService;
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
        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(PLN_CODE,GOLD_CODE,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(GOLD_CODE, PLN_CODE, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/latest/{base}/{target}")
    ResponseEntity<ExchangeRateDTO> createTodayRateUrl(
            @PathVariable String base,
            @PathVariable String target) {

        String date = String.valueOf(LocalDate.now(ZoneId.of("GMT")));
        System.out.println(date);

        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(base,target,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(base.toUpperCase(), target.toUpperCase(), date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }


    @GetMapping("/exchange/latest/gold")
    ResponseEntity<ExchangeRateDTO> createTodayGoldUrl(
            ) {
        String date = String.valueOf(LocalDate.now(ZoneId.of("GMT")));
        statisticService.saveStatisticModelToDb(new ExchangeStatisticModel(PLN_CODE,GOLD_CODE,date));

        ExchangeRateDTO result = exchangeRateService.getExchangeDataToView(GOLD_CODE, PLN_CODE, date);
        return ResponseEntity.created(URI.create("/" + result.getBaseCurrency())).body(result);
    }

    @GetMapping("/exchange/all/currency")
    ResponseEntity<Iterable<ExchangeRate>> readAllExchangeRate() {
        return ResponseEntity.ok(exchangeRateService.findAll());
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

    @GetMapping("/statistic/all/queries")
    ResponseEntity<Iterable<ExchangeStatisticModel>> readAllStatistic() {
        return ResponseEntity.ok(statisticService.findAll());
    }



    @DeleteMapping(value = "/statistic/remove")
    public ResponseEntity<?> cleanStatisticBD() {

        statisticService.cleanStatisticDb();
        return ResponseEntity.noContent().build();

    }


}
