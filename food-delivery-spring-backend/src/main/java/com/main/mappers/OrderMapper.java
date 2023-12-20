package com.main.mappers;

import com.main.dto.OrderDto;
import com.main.entity.Order;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(OrderMapperDecorator.class)
public interface OrderMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "customerEmail")
    OrderDto orderToOrderDto(Order order);
}
