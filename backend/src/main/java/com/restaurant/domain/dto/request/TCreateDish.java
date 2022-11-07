package com.restaurant.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.math.BigDecimal;
import java.util.UUID;

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
