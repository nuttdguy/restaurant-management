package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.CreateDishTo;
import com.restaurant.domain.dto.response.CreateDishResponse;
import com.restaurant.domain.model.Dish;

import java.math.BigDecimal;

public class DishMapper {

    private DishMapper() { }

    public static Dish toDishFrom(CreateDishTo createDishTo) {
        Dish dish = new Dish();
        dish.setName(createDishTo.dishName());
        dish.setDescription(createDishTo.description());
        dish.setPrice(new BigDecimal(String.valueOf(createDishTo.price())));
        dish.setIngredients(createDishTo.ingredients());
        return dish;
    }

    public static CreateDishResponse toCreateDishFrom(Dish dish) {
        return new CreateDishResponse(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice().floatValue(),
                dish.getRestaurant().getUuid()
        );
    }



}
