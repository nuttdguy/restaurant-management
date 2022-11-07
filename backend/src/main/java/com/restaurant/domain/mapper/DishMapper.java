package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateDish;
import com.restaurant.domain.dto.response.VwDish;
import com.restaurant.domain.model.Dish;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class DishMapper {

    private DishMapper() { }

    public static Dish toDishFrom(TCreateDish tCreateDish) {
        return Dish.builder()
                .name(tCreateDish.dishName())
                .category(tCreateDish.category())
                .tags("")
                .description(tCreateDish.description())
                .ingredients(tCreateDish.ingredients())
                .price(tCreateDish.price())
//                .price(new BigDecimal(String.valueOf(tCreateDish.price())))
                .build();
//        Dish dish = new Dish();
//        dish.setName(tCreateDish.dishName());
//        dish.setIngredients(tCreateDish.ingredients());
//        dish.setDescription(tCreateDish.description());
//        dish.setPrice(new BigDecimal(String.valueOf(tCreateDish.price())));
//        return dish;
    }

    public static VwDish toCreateDishFrom(Dish dish) {
        log.trace("toCreateDishFrom {}", dish);
        return new VwDish(
                dish.getId(),
                dish.getName(),
                dish.getCategory(),
                dish.getDescription(),
                dish.getIngredients(),
                dish.getTags(),
//                dish.getPhotos().toString(),
                dish.getPrice().floatValue(),
                dish.getRestaurant().getUuid()
        );
    }



}
