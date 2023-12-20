package com.main.service;

import com.main.dto.OrderStatisticsDto;
import com.main.dto.ProductStatistics;
import com.main.repository.OrderRepository;
import com.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Integer getTotalRevenue(LocalDate date) {
       return orderRepository.getTotalRevnue(date);
    }

    @Override
    public List<ProductStatistics> getMostSelledProducts(LocalDate date) {
        return orderRepository.findTop4ProductStatistics(date);
    }

    @Override
    public Long getNumberOfAccounts() {
        return userRepository.count();
    }

    @Override
    public List<OrderStatisticsDto> getOrderStatistic(LocalDate date) {
        return orderRepository.findOrderStatusStatistics(date);
    }
}
