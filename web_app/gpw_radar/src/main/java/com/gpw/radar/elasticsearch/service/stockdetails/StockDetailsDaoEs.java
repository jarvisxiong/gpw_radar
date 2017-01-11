package com.gpw.radar.elasticsearch.service.stockdetails;

import com.gpw.radar.elasticsearch.domain.stockdetails.StockDetails;
import com.gpw.radar.elasticsearch.repository.StockDetailsEsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockDetailsDaoEs implements StockDetailsDao {

    private final StockDetailsEsRepository stockDetailsEsRepository;

    @Autowired
    public StockDetailsDaoEs(StockDetailsEsRepository stockDetailsEsRepository) {
        this.stockDetailsEsRepository = stockDetailsEsRepository;
    }

    @Override
    public LocalDate findTopDate() {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "date"));
        StockDetails topByOrderByDateDesc = stockDetailsEsRepository.findAll(new PageRequest(0, 1, sort))
            .getContent()
            .stream()
            .findFirst()
            .get();
        return topByOrderByDateDesc.getDate();
    }

    @Override
    public Iterable<StockDetails> save(Iterable<StockDetails> stockDetails) {
        return stockDetailsEsRepository.save(stockDetails);
    }

    @Override
    public StockDetails save(StockDetails stockDetails) {
        return stockDetailsEsRepository.save(stockDetails);
    }

    @Override
    public List<StockDetails> findByStockTickerOrderByDateDesc(String ticker, Pageable pageable) {
        Page<StockDetails> byStockTickerOrderByDateDesc = stockDetailsEsRepository.findByStockTickerOrderByDateDesc(ticker, pageable);
        return byStockTickerOrderByDateDesc.getContent();
    }

}