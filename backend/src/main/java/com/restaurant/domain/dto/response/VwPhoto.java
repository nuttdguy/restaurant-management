package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwPhoto(
        Long id,
        String name,
        String type,
        String photoUrl
) {

}
