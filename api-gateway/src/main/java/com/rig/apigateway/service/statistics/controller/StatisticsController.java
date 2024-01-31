package com.rig.apigateway.service.statistics.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.statistics.client.StatisticsServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@AllArgsConstructor
@Tag(
        name = "Statistics Controller",
        description = "Responsible for statistic related operations."
)
@SecurityRequirement(name = "API Key")
@SecurityRequirement(name = "Bearer Authentication")
public final class StatisticsController {

    private final StatisticsServiceClient statisticsServiceClient;

    @GetMapping("/{customerId}")
    @Operation(summary = "Returns monthly order statistics of customer.")
    public List<ObjectNode> getMonthlyOrderStatisticsOfCustomer(@PathVariable final int customerId) {
        return statisticsServiceClient.getMonthlyOrderStatisticsOfCustomer(customerId);
    }

}
