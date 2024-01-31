package com.rig.statisticsservice.service;

import com.rig.statisticsservice.data.payload.CustomerOrderStatisticsPerMonth;
import com.rig.statisticsservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerOrderStatisticsPerMonth> getMonthlyOrderStatisticsOfCustomer(final long customerId) {
        return orderRepository.getMonthlyOrderStatisticsOfCustomer(customerId)
                .stream()
                .map(statistic -> new CustomerOrderStatisticsPerMonth().from(statistic))
                .collect(Collectors.toList());
    }

}
