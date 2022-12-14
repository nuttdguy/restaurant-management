package com.restaurant.domain.dto.request;

import java.math.BigDecimal;

public record TEditDish(
        Long id,
        String name,
        BigDecimal price,
        String ingredients,
        String description
){
}
