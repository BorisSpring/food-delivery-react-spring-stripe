package com.main.mappers;

import com.main.dto.OrderDto;
import com.main.dto.OrderItemDto;
import com.main.entity.Order;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public abstract class OrderMapperDecorator implements OrderMapper {

    @Autowired
    private  OrderMapper orderMapper;
    public OrderMapperDecorator(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        List<OrderItemDto> list = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            list.add(OrderItemDto.builder()
                    .image(orderItem.getProduct().getImage())
                    .name(orderItem.getProduct().getName())
                    .totalPrice(orderItem.getTotalPrice())
                    .quantity(orderItem.getQuantity())
                    .build());
        });
        orderDto.setOrderItems(list);
        return orderDto;
    }
}
