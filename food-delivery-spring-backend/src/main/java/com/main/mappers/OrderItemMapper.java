package com.main.mappers;

import com.main.dto.OrderItemDto;
import com.main.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderItemMapper {


    @Mapping(source = "product.name", target="name")
    @Mapping(source = "product.image", target = "image" )
    OrderItemDto orderItemToDto(OrderItem orderItem);
}
