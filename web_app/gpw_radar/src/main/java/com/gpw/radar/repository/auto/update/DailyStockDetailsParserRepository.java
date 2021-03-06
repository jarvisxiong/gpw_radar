package com.gpw.radar.repository.auto.update;

import com.gpw.radar.domain.database.DailyStockDetailsParser;
import com.gpw.radar.service.database.ParserMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyStockDetailsParserRepository extends JpaRepository<DailyStockDetailsParser, ParserMethod> {

    @Query(value = "select * from stock_details_parser_method limit 1", nativeQuery = true)
    DailyStockDetailsParser findMethod();

    @Modifying
    @Query(value = "update stock_details_parser_method set method = :method", nativeQuery = true)
    void setStockDetailsParserMethod(@Param("method") String method);
}
