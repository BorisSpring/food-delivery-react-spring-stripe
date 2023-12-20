package com.main.service;

import com.main.dto.OrderStatisticsDto;
import com.main.dto.ProductStatistics;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {

     Integer getTotalRevenue(LocalDate date);

     List<ProductStatistics> getMostSelledProducts(LocalDate date);

     Long getNumberOfAccounts();

     List<OrderStatisticsDto> getOrderStatistic(LocalDate date);
}
