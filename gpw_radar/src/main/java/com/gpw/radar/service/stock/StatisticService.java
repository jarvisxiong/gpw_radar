package com.gpw.radar.service.stock;

import com.gpw.radar.repository.stock.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StatisticService {

	@Inject
	private StockRepository stockRepository;

	public ResponseEntity<Long> countStocksUp() {
		long countUp = stockRepository.countUpStocks();
		return new ResponseEntity<Long>(countUp, HttpStatus.OK);
	}

	public ResponseEntity<Long> countStocksDown() {
		long countDown = stockRepository.countDownStocks();
		return new ResponseEntity<Long>(countDown, HttpStatus.OK);
	}

	public ResponseEntity<Long> countStocksNoChange() {
		long countNoChange = stockRepository.countNoChangeStocks();
		return new ResponseEntity<Long>(countNoChange, HttpStatus.OK);
	}

}