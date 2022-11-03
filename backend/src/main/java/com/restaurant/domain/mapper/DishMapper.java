package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateDish;
import com.restaurant.domain.dto.response.VwDish;
import com.restaurant.domain.model.Dish;

import java.math.BigDecimal;

public class DishMapper {

    private DishMapper() { }

    public static Dish toDishFrom(TCreateDish TCreateDish) {
        Dish dish = new Dish();
        dish.setName(TCreateDish.dishName());
        dish.setDescription(TCreateDish.description());
        dish.setPrice(new BigDecimal(String.valueOf(TCreateDish.price())));
        dish.setIngredients(TCreateDish.ingredients());
        return dish;
    }

    public static VwDish toCreateDishFrom(Dish dish) {
        return new VwDish(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice().floatValue(),
                dish.getRestaurant().getUuid()
        );
    }



}
