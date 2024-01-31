package com.rig.statisticsservice.controller;

import com.rig.statisticsservice.data.payload.CustomerOrderStatisticsPerMonth;
import com.rig.statisticsservice.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@AllArgsConstructor
public final class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{customerId}")
    public List<CustomerOrderStatisticsPerMonth> getMonthlyOrderStatisticsOfCustomer(@PathVariable final int customerId) {
        return statisticsService.getMonthlyOrderStatisticsOfCustomer(customerId);
    }

}
