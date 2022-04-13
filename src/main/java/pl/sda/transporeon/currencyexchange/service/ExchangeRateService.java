package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.model.ExchangeRate;
import pl.sda.transporeon.currencyexchange.model.ExchangeRateDTO;
import pl.sda.transporeon.currencyexchange.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ApiServiceNonPln apiServiceNonPln;
    private final ApiServicePln apiServicePln;
    private  final MapToDTO mapToDTO;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, ApiServiceNonPln apiServiceNonPln, ApiServicePln apiServicePln, MapToDTO mapToDTO) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.apiServiceNonPln = apiServiceNonPln;
        this.apiServicePln = apiServicePln;
        this.mapToDTO = mapToDTO;
    }


    public ExchangeRateDTO getExchangeDataToView(String base,
                                                 String target,
                                                 String date){
        ExchangeRate rate = new ExchangeRate();
        //pobieranie walut z api TODO
        if(base.equals("PLN")){
             rate = apiServicePln.getRate(base, target, date);
        }else {
             rate = apiServiceNonPln.getRate(base, target, date);
        }
        //zapis do DB
        exchangeRateRepository.save(rate);
        // mapowanie model do dto
        ExchangeRateDTO rateDTO = mapToDTO.mapToDto(rate);
        //return Dto

        return rateDTO;
    }






}
