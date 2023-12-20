package com.main.repository;

import com.main.dto.OrderStatisticsDto;
import com.main.dto.ProductStatistics;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.entity.Order;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{


   @Query("SELECT sum(o.totalPrice) FROM Order o WHERE o.isPaid = true AND (:date IS NULL or o.created > :date)")
   Integer getTotalRevnue(@Nullable  @Param("date")LocalDate date);

   @Query("SELECT new com.main.dto.ProductStatistics(p,SUM(oi.quantity)) FROM Order o " +
           "JOIN o.orderItems oi " +
           "JOIN oi.product p " +
           "WHERE (:date IS NULL or o.created >:date)" +
           "GROUP BY p.id " +
           "ORDER BY SUM(oi.quantity) DESC LIMIT 4")
   List<ProductStatistics> findTop4ProductStatistics(@Param("date") LocalDate date);


   @Query("SELECT new com.main.dto.OrderStatisticsDto(o.orderStatus, COUNT(o.id)) FROM Order o " +
           "WHERE (:date IS NULL OR o.created >:date) " +
           "GROUP BY  o.orderStatus " +
           "ORDER BY COUNT(o.id) DESC ")
   List<OrderStatisticsDto> findOrderStatusStatistics(@Param("date") LocalDate date);
}
