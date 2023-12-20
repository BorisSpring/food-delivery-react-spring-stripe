package com.main.controllers;

import com.main.dto.OrderStatisticsDto;
import com.main.dto.ProductStatistics;
import com.main.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@Validated
@RequiredArgsConstructor
public class OverviewController {

    private final  StatisticService statisticService;

    @GetMapping("/revenue")
    public ResponseEntity<Integer> getTotalRevenue(@RequestParam(required = false, name = "date")LocalDate date){
        return ResponseEntity.ok(statisticService.getTotalRevenue(date));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductStatistics>> findMostSelledProducts(@RequestParam(name = "date", required = false) LocalDate date){
        return  ResponseEntity.ok(statisticService.getMostSelledProducts(date));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderStatisticsDto>> findOrderStatisticHandler(@RequestParam(name = "date", required = false) LocalDate date){
        return  ResponseEntity.ok(statisticService.getOrderStatistic(date));
    }

    @GetMapping("/users")
    public ResponseEntity<Long> findNumberOfAccounts(){
        return  ResponseEntity.ok(statisticService.getNumberOfAccounts());
    }
}
