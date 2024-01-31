package com.rig.statisticsservice.data.payload;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
public final class CustomerOrderStatisticsPerMonth {

    private String month;
    private Long totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPurchasedAmount;
    
    public CustomerOrderStatisticsPerMonth from(final Map<String, Object> statistic) {
        this.month = statistic.get("month").toString().strip();
        this.totalOrderCount = (Long) statistic.get("total_order_count");
        this.totalBookCount = (Long) statistic.get("total_book_count");
        this.totalPurchasedAmount = (BigDecimal) statistic.get("total_purchased_amount");
        
        return this;
    }

}
