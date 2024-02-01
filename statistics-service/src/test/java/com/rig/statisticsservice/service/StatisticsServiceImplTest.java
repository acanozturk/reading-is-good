package com.rig.statisticsservice.service;

import com.rig.statisticsservice.MockedTest;
import com.rig.statisticsservice.data.payload.CustomerOrderStatisticsPerMonth;
import com.rig.statisticsservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StatisticsServiceImplTest extends MockedTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void testGetMonthlyOrderStatisticsOfCustomer() {
        final long customerId = 1L;
        final Map<String, Object> statisticMap = Map.of(
                "month", "January",
                "total_order_count", 100L,
                "total_book_count", 10L,
                "total_purchased_amount", BigDecimal.valueOf(1000.50)
        );

        final CustomerOrderStatisticsPerMonth customerOrderStatisticsPerMonth = new CustomerOrderStatisticsPerMonth();
        customerOrderStatisticsPerMonth.setMonth("January");
        customerOrderStatisticsPerMonth.setTotalOrderCount(100L);
        customerOrderStatisticsPerMonth.setTotalBookCount(10L);
        customerOrderStatisticsPerMonth.setTotalPurchasedAmount(BigDecimal.valueOf(1000.50));
        
        when(orderRepository.getMonthlyOrderStatisticsOfCustomer(customerId)).thenReturn(List.of(statisticMap));

        final List<CustomerOrderStatisticsPerMonth> result = statisticsService.getMonthlyOrderStatisticsOfCustomer(customerId);

        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(customerOrderStatisticsPerMonth));
        verify(orderRepository, times(1)).getMonthlyOrderStatisticsOfCustomer(anyLong());
    }
    
    
}