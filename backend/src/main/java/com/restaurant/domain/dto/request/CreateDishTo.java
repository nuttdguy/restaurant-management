package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record CreateDishTo(
        @NotBlank String itemName,
        @NotBlank BigDecimal price,
        String description,
        String ingredients
        ) {

}
