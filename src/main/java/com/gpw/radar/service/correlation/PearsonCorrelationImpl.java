package com.gpw.radar.service.correlation;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import com.gpw.radar.domain.StockStatistic;
import com.gpw.radar.domain.enumeration.StockTicker;
import com.gpw.radar.repository.StockDetailsRepository;

public class PearsonCorrelationImpl extends CorrelationVariables implements Correlation {

	private PearsonsCorrelation pearsonsCorrelation;

	public PearsonCorrelationImpl(StockTicker correlationForTicker, int period, StockDetailsRepository stockDetailsRepository) {
		super(correlationForTicker, period, stockDetailsRepository);
		pearsonsCorrelation = new PearsonsCorrelation();
	}

	public void compute(StockTicker ticker) {
		double[] closePricesToCompare = getClosePrices(getContent(ticker));
		Double correlation = 0.0;
		if (closePricesToCompare.length == sourceClosePrices.length) {
			correlation = pearsonsCorrelation.correlation(sourceClosePrices, closePricesToCompare);
		}
		StockStatistic stockCorrelation = new StockStatistic(correlation, ticker);
		correlationTreeSet.add(stockCorrelation);
	}
}
