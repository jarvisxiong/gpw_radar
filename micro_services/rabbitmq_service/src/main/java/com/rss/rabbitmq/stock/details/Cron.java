package com.rss.rabbitmq.stock.details;

import com.rss.rabbitmq.service.JsonConverter;
import com.stock.details.updater.model.StockDetails;
import com.stock.details.updater.parser.gpw.HtmlParser;
import com.stock.details.updater.parser.gpw.WebStockDetailsParser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("stockDetailsCron")
public class Cron {

    @Value("${local_date_format}")
    private String localDateFormat;

    private DateTimeFormatter formatter;
    private final Producer producer;
    private final HtmlParser htmlParser;
    private final WebStockDetailsParser gpwParser;
    private final JsonConverter<StockDetails> jsonConverter;

    @Autowired
    public Cron(@Qualifier("stockDetailsSender") Producer producer, JsonConverter<StockDetails> jsonConverter,
                HtmlParser htmlParser, WebStockDetailsParser gpwParser) {
        this.producer = producer;
        this.jsonConverter = jsonConverter;
        this.htmlParser = htmlParser;
        this.gpwParser = gpwParser;
    }

    @PostConstruct
    public void init() {
        formatter = DateTimeFormatter.ofPattern(localDateFormat);
    }

    @Scheduled(cron = "0 30 17 ? * MON-FRI")
    public void fireCron() throws IOException {
        List<StockDetails> currentStocksDetails = parseStocksDetailsFromWeb();
        String stockDetailsDate = currentStocksDetails.get(0).getDate().format(formatter);
        String json = jsonConverter.convertToJson(currentStocksDetails);
        producer.publish(json, stockDetailsDate);
    }

    public List<StockDetails> parseStocksDetailsFromWeb() throws IOException {
        Elements tableRowsContentFromWeb = htmlParser.getTableRowsContentFromWeb();
        LocalDate currentDateOfStockDetails = htmlParser.getCurrentDateOfStockDetails();
        List<StockDetails> currentStocksDetails = gpwParser.getCurrentStockDetails(tableRowsContentFromWeb, currentDateOfStockDetails);
        return currentStocksDetails;
    }
}
