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
    private final ApiServiceGold apiServicePln;
    private  final MapToDTO mapToDTO;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository, ApiServiceNonPln apiServiceNonPln, ApiServiceGold apiServicePln, MapToDTO mapToDTO) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.apiServiceNonPln = apiServiceNonPln;
        this.apiServicePln = apiServicePln;
        this.mapToDTO = mapToDTO;
    }


    public ExchangeRateDTO getExchangeDataToView(String base,
                                                 String target,
                                                 String date){
        ExchangeRate rate;
        if(base.equals("gold")){
             rate = apiServicePln.getRate(base, target, date);
        }else {
             rate = apiServiceNonPln.getRate(base, target, date);
        }
        exchangeRateRepository.save(rate);
        ExchangeRateDTO rateDTO = mapToDTO.mapToDto(rate);

        return rateDTO;
    }

    public void remove(){

        exchangeRateRepository.deleteAll(exchangeRateRepository.findAll());

    }

}
