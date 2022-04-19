package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticDTO;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticModel;
import pl.sda.transporeon.currencyexchange.repository.ExchangeStatisticRepository;

import java.util.*;

@Service
public class ExchangeStatisticService {

    private final ExchangeStatisticRepository statisticRepository;


    @Autowired
    public ExchangeStatisticService(ExchangeStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }


    public ExchangeStatisticDTO calculateStatistic(String base, String target, String date) {

        List<ExchangeStatisticModel> listStatistic = new ArrayList<>((Collection<? extends ExchangeStatisticModel>) statisticRepository.findAll());

        int counter = 0;

        for (ExchangeStatisticModel statModel : listStatistic) {
            if (statModel.getBaseCurrency().equals(base) && statModel.getTargetCurrency().equals(target) && statModel.getExchangeDate().equals(date)) {
                counter++;
            }
        }
        return new ExchangeStatisticDTO(base, target, date, counter);

    }

    public void saveStatisticModelToDb(ExchangeStatisticModel statisticModel) {
        statisticRepository.save(statisticModel);

    }

    public void cleanStatisticDb() {
        statisticRepository.deleteAll(statisticRepository.findAll());
    }


}
