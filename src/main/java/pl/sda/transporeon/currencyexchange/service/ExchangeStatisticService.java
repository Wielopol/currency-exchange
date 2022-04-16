package pl.sda.transporeon.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticDTO;
import pl.sda.transporeon.currencyexchange.model.ExchangeStatisticModel;
import pl.sda.transporeon.currencyexchange.repository.ExchangeStatisticRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ExchangeStatisticService {

    private final ExchangeStatisticRepository statisticRepository;

    @Autowired
    public ExchangeStatisticService(ExchangeStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }


    public ExchangeStatisticDTO calculateStatistic(String base, String target, String date) {
        //pobiera dane z db
        List<ExchangeStatisticModel> listStatistic = new ArrayList<>();
        listStatistic.addAll((Collection<? extends ExchangeStatisticModel>) statisticRepository.findAll());

        //filtrowanie po parametrach
        int counter = 0;

        for (ExchangeStatisticModel statModel : listStatistic) {
            if (statModel.getBaseCurrency().equals(base) && statModel.getTargetCurrency().equals(target) && statModel.getExchangeDate().equals(date)) {
                counter++;
            }
        }
        //zwracamy ilość
        ExchangeStatisticDTO result = new ExchangeStatisticDTO(base, target, date, counter);
        return result;

    }

    public void saveStatisticModelToBd(ExchangeStatisticModel statisticModel) {
        statisticRepository.save(statisticModel);

    }

    public void cleanStatisticDb() {
        statisticRepository.deleteAll(statisticRepository.findAll());
    }


}
