package com.gpw.radar.service.database;

import com.gpw.radar.domain.stock.Stock;
import com.gpw.radar.domain.stock.StockDetails;
import com.gpw.radar.domain.stock.StockFiveMinutesDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StockDetailsTextFileParserService {


    private final String cvsSplitBy = ",";

    @Inject
    private WebParserService webParserService;

    public Set<StockDetails> parseStockDetailsByStockFromTxtFile(Stock stock) {
        String line = "";
        Set<StockDetails> stockDetailsList = new HashSet<>();
        BufferedReader in = null;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String filePath = "stocks_data/daily/pl/wse_stocks/" + stock.getTicker().name() + ".txt";
            FileReader fileIn = new FileReader(classLoader.getResource(filePath).getFile());

            in = new BufferedReader(fileIn);

            in.readLine();
            while ((line = in.readLine()) != null) {
                StockDetails stockDetails = new StockDetails();
                String[] stockdetails = line.split(cvsSplitBy);
                stockDetails.setStock(stock);

                stockDetails.setDate(webParserService.parseLocalDateFromString(stockdetails[0]));
                stockDetails.setOpenPrice(new BigDecimal(stockdetails[1]));
                stockDetails.setMaxPrice(new BigDecimal(stockdetails[2]));
                stockDetails.setMinPrice(new BigDecimal(stockdetails[3]));
                stockDetails.setClosePrice(new BigDecimal(stockdetails[4]));
                try {
                    stockDetails.setVolume(Long.valueOf(stockdetails[5]));
                } catch (ArrayIndexOutOfBoundsException exc) {
                    stockDetails.setVolume(0l);
                }
                stockDetailsList.add(stockDetails);
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stockDetailsList;
    }

    public List<StockFiveMinutesDetails> parseStockFiveMinutesDetailsByStockFromTxtFile(Stock stock, LocalDate startDate) {
        String line = "";
        List<StockFiveMinutesDetails> stockFiveMinutesDetailsList = new ArrayList<>();
        BufferedReader in = null;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String filePath = "stocks_data/5min/pl/wse_stocks/" + stock.getTicker().name() + ".txt";
            FileReader fileIn = new FileReader(classLoader.getResource(filePath).getFile());

            in = new BufferedReader(fileIn);
            in.readLine();

            while ((line = in.readLine()) != null) {
                StockFiveMinutesDetails stockFiveMinutesDetails = new StockFiveMinutesDetails();
                String[] stockFiveMinuteDetails = line.split(cvsSplitBy);
                stockFiveMinutesDetails.setStock(stock);
                LocalDate dateDetail = webParserService.parseLocalDateFromString(stockFiveMinuteDetails[0]);
                LocalTime timeDetail = webParserService.parseLocalTimeFromString(stockFiveMinuteDetails[1]);
                if (dateDetail.isBefore(startDate)) {
                    continue;
                }

                stockFiveMinutesDetails.setDate(LocalDateTime.of(dateDetail, timeDetail));
                stockFiveMinutesDetails.setTime(timeDetail);
                stockFiveMinutesDetails.setCumulatedVolume(0l);
                try {
                    stockFiveMinutesDetails.setVolume(Long.valueOf(stockFiveMinuteDetails[6]));
                } catch (ArrayIndexOutOfBoundsException exc) {
                    stockFiveMinutesDetails.setVolume(0l);
                }
                stockFiveMinutesDetailsList.add(stockFiveMinutesDetails);
            }

            stockFiveMinutesDetailsList = fillEmptyTimeAndCumulativeVolume(stockFiveMinutesDetailsList);

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stockFiveMinutesDetailsList;
    }

    private List<StockFiveMinutesDetails> fillEmptyTimeAndCumulativeVolume(List<StockFiveMinutesDetails> stockFiveMinutesDetailsList) {
        List<StockFiveMinutesDetails> filledEmptyTimeAndCumulativeVolume = new ArrayList<>();

        LocalDate startDate = stockFiveMinutesDetailsList.get(0).getDate().toLocalDate().minusDays(1);

        int i = 0;
        for (StockFiveMinutesDetails element : stockFiveMinutesDetailsList) {
            if (!element.getDate().toLocalDate().isEqual(startDate)) {
                startDate = element.getDate().toLocalDate();
                element.setCumulatedVolume(element.getVolume());
                filledEmptyTimeAndCumulativeVolume.add(element);
                i++;
                continue;
            }

            int previousElementToCompare = i - 1;
            LocalTime timeOfEvent = element.getTime();
            LocalTime timeOfPreviousEvent = stockFiveMinutesDetailsList.get(previousElementToCompare).getTime();
            int differenceInSeconds = timeOfEvent.toSecondOfDay() - timeOfPreviousEvent.toSecondOfDay();
            int differenceInMinutes = differenceInSeconds / 60;
            if (differenceInMinutes > 5) {
                int lengthOfDifference = differenceInMinutes / 5;
                for (int j = 1; j < lengthOfDifference; j++) {
                    StockFiveMinutesDetails emptyFiveMinutesDetails = new StockFiveMinutesDetails();
                    emptyFiveMinutesDetails.setDate(stockFiveMinutesDetailsList.get(previousElementToCompare).getDate().plusMinutes(5*j));
                    emptyFiveMinutesDetails.setTime(timeOfPreviousEvent.plusMinutes(5));
                    emptyFiveMinutesDetails.setVolume(0l);
                    emptyFiveMinutesDetails.setCumulatedVolume(stockFiveMinutesDetailsList.get(previousElementToCompare).getCumulatedVolume());
                    emptyFiveMinutesDetails.setStock(element.getStock());
                    filledEmptyTimeAndCumulativeVolume.add(emptyFiveMinutesDetails);
                }
            }
            element.setCumulatedVolume(element.getVolume() + filledEmptyTimeAndCumulativeVolume.get(filledEmptyTimeAndCumulativeVolume.size() - 1).getCumulatedVolume());
            filledEmptyTimeAndCumulativeVolume.add(element);
            i++;
        }
        return filledEmptyTimeAndCumulativeVolume;
    }
}
