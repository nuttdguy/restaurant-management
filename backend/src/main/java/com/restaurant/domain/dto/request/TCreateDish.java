package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record TCreateDish(
        String phone,
        @NotBlank String dishName,
        @NotBlank BigDecimal price,
        String category,
        String description,
        String ingredients,
        String username
        ) {

}
