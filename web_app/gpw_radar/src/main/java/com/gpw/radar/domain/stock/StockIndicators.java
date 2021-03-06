package com.gpw.radar.domain.stock;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "STOCK_INDICATORS")
public class StockIndicators {

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.gpw.radar.domain.generator.StringIdGenerator")
    @GeneratedValue(generator = "seq_id")
    private String id;

    @Column(name = "slope_simple_regression_10")
    private double slopeSimpleRegression10Days = 0.0;

    @Column(name = "slope_simple_regression_30")
    private double slopeSimpleRegression30Days = 0.0;

    @Column(name = "slope_simple_regression_60")
    private double slopeSimpleRegression60Days = 0.0;

    @Column(name = "slope_simple_regression_90")
    private double slopeSimpleRegression90Days = 0.0;

    @Column(name = "average_volume_10_days", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageVolume10Days = new BigDecimal(0);

    @Column(name = "average_volume_30_days", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageVolume30Days = new BigDecimal(0);

    @Column(name = "volume_ratio_10", precision = 10, scale = 2, nullable = false)
    private BigDecimal volumeRatio10 = new BigDecimal(0);

    @Column(name = "volume_ratio_30", precision = 10, scale = 2, nullable = false)
    private BigDecimal volumeRatio30 = new BigDecimal(0);

    @Column(name = "percent_return", precision = 10, scale = 2, nullable = false)
    private BigDecimal percentReturn = new BigDecimal(0);

    @Column(name = "volume_value_30_days", precision = 25, scale = 2)
    private BigDecimal volumeValue30Days = new BigDecimal(0);

    private LocalDate date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSlopeSimpleRegression10Days() {
        return slopeSimpleRegression10Days;
    }

    public void setSlopeSimpleRegression10Days(double slopeSimpleRegression10Days) {
        this.slopeSimpleRegression10Days = slopeSimpleRegression10Days;
    }

    public double getSlopeSimpleRegression30Days() {
        return slopeSimpleRegression30Days;
    }


    public void setSlopeSimpleRegression30Days(double slopeSimpleRegression30Days) {
        this.slopeSimpleRegression30Days = slopeSimpleRegression30Days;
    }

    public double getSlopeSimpleRegression60Days() {
        return slopeSimpleRegression60Days;
    }

    public void setSlopeSimpleRegression60Days(double slopeSimpleRegression60Days) {
        this.slopeSimpleRegression60Days = slopeSimpleRegression60Days;
    }

    public double getSlopeSimpleRegression90Days() {
        return slopeSimpleRegression90Days;
    }

    public void setSlopeSimpleRegression90Days(double slopeSimpleRegression90Days) {
        this.slopeSimpleRegression90Days = slopeSimpleRegression90Days;
    }

    public BigDecimal getAverageVolume10Days() {
        return averageVolume10Days;
    }

    public void setAverageVolume10Days(BigDecimal averageVolume10Days) {
        this.averageVolume10Days = averageVolume10Days;
    }

    public BigDecimal getAverageVolume30Days() {
        return averageVolume30Days;
    }

    public void setAverageVolume30Days(BigDecimal averageVolume30Days) {
        this.averageVolume30Days = averageVolume30Days;
    }

    public BigDecimal getVolumeRatio10() {
        return volumeRatio10;
    }

    public void setVolumeRatio10(BigDecimal volumeRatio10) {
        this.volumeRatio10 = volumeRatio10;
    }

    public BigDecimal getVolumeRatio30() {
        return volumeRatio30;
    }

    public void setVolumeRatio30(BigDecimal volumeRatio30) {
        this.volumeRatio30 = volumeRatio30;
    }

    public BigDecimal getPercentReturn() {
        return percentReturn;
    }

    public void setPercentReturn(BigDecimal percentReturn) {
        this.percentReturn = percentReturn;
    }

    public BigDecimal getVolumeValue30Days() {
        return volumeValue30Days;
    }

    public void setVolumeValue30Days(BigDecimal volumeValue30Days) {
        this.volumeValue30Days = volumeValue30Days;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
