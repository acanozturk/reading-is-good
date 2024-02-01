package com.rig.statisticsservice.controller;

import com.rig.statisticsservice.data.payload.CustomerOrderStatisticsPerMonth;
import com.rig.statisticsservice.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StatisticsControllerTest extends ControllerTest {

    @MockBean
    private StatisticsService statisticsService;

    @Test
    void testGetMonthlyOrderStatisticsOfCustomer() throws Exception {
        final List<CustomerOrderStatisticsPerMonth> customerOrderStatisticsPerMonths = List.of(new CustomerOrderStatisticsPerMonth());

        when(statisticsService.getMonthlyOrderStatisticsOfCustomer(anyLong())).thenReturn(customerOrderStatisticsPerMonths);

        mockMvc.perform(
                        get("/api/v1/statistics/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(getAsJson(customerOrderStatisticsPerMonths)));

        verify(statisticsService, times(1)).getMonthlyOrderStatisticsOfCustomer(anyLong());
    }

}