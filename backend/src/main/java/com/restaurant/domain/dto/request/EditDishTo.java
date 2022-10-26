package com.restaurant.domain.dto.request;

import java.math.BigDecimal;

public record EditDishTo(
        String itemName,
        BigDecimal price,
        String ingredients,
        String description
){
}
