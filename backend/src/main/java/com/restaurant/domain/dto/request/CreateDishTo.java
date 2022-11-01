package com.restaurant.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateDishTo(
        UUID restaurantId,
        @NotBlank String dishName,
        @NotBlank BigDecimal price,
        String category,
        String description,
        String ingredients,
        @JsonIgnore MultipartFile image,
        String username
        ) {

}
