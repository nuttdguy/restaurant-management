package com.restaurant.domain.dto.request;

import java.math.BigDecimal;

public record EditDishTo(
        Long id,
        String itemName,
        BigDecimal price,
        String ingredients,
        String description
){
}
