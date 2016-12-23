package com.gpw.radar.elasticsearch.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gpw.radar.domain.util.BigDecimalDeserializer;
import com.gpw.radar.domain.util.BigDecimalSerializer;
import com.gpw.radar.domain.util.CustomLocalDateDeserializer;
import com.gpw.radar.domain.util.CustomLocalDateSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(indexName = "stock_details", type = "daily", shards = 5, replicas = 0)
public class StockDetails {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate date;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal openPrice;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal maxPrice;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal minPrice;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal closePrice;

    @Field(type = FieldType.Long)
    private Long transactionsNumber = 0L;

    @Field(type = FieldType.Long)
    private Long volume;

    private Stock stock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Long getTransactionsNumber() {
        return transactionsNumber;
    }

    public void setTransactionsNumber(Long transactionsNumber) {
        this.transactionsNumber = transactionsNumber;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}