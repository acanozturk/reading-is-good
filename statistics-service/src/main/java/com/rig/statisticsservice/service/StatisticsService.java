package com.rig.statisticsservice.service;

import com.rig.statisticsservice.data.payload.CustomerOrderStatisticsPerMonth;

import java.util.List;

public interface StatisticsService {
    
    List<CustomerOrderStatisticsPerMonth> getMonthlyOrderStatisticsOfCustomer(long customerId);
    
}
