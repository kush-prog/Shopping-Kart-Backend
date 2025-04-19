package com.kush.shoppingkart.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {

    private Long Id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String Status;
    private Set<OrderItemDto> items;


}
