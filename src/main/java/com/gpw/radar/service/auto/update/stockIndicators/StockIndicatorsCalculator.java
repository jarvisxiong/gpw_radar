package com.gpw.radar.service.auto.update.stockIndicators;

import java.util.List;

import com.gpw.radar.domain.StockIndicators;

public interface StockIndicatorsCalculator {
	List<StockIndicators> calculateCurrentStockIndicators();
}