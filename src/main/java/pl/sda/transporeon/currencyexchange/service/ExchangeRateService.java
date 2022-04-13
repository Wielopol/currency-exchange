package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ApiServiceNonPln apiServiceNonPln;
    private final ApiServicePln apiServicePln;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, ApiServiceNonPln apiServiceNonPln, ApiServicePln apiServicePln) {
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,
                               ExchangeRateApi exchangeRateApi) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.apiServiceNonPln = apiServiceNonPln;
        this.apiServicePln = apiServicePln;
        this.exchangeRateApi = exchangeRateApi;
//        this.mapToDTO = mapToDTO;
    }

    public ExchangeRate getExchangeDataToView(String base,
                                                 String target,
                                                 double casch,
                                                 Date data){
        //pobieranie walut z api TODO
        ExchangeRate rate = exchangeRateApi.getExchangeRate(base, target, casch, data);
        //zapis do DB
        exchangeRateRepository.save(rate);
        // mapowanie model do dto
//       ExchangeRateDTO rateDTO =  mapToDTO.mapToDto(rate);

        //return Dto
//        return rateDTO;
        return rate;
    }






}
