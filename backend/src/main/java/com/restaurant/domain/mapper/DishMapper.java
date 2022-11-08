package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateDish;
import com.restaurant.domain.dto.response.VwDish;
import com.restaurant.domain.dto.response.VwPhoto;
import com.restaurant.domain.model.Dish;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class DishMapper {

    private DishMapper() { }

    public static Dish toDishFrom(TCreateDish tCreateDish) {
        return Dish.builder()
                .name(tCreateDish.dishName())
                .category(tCreateDish.category())
                .description(tCreateDish.description())
                .ingredients(tCreateDish.ingredients())
                .price(tCreateDish.price())
                .photos(new HashSet<>())
                .build();
    }

    public static VwDish toCreateDishFrom(Dish dish) {
        log.trace("toCreateDishFrom {}", dish);
        return VwDish.builder()
                .id(dish.getId())
                .name(dish.getName())
                .category(dish.getCategory())
                .description(dish.getDescription())
                .ingredients(dish.getIngredients())
                .tags(dish.getTags())
                .photos(dish.getPhotos().stream().map(photo ->
                        VwPhoto.builder()
                                .id(photo.getId())
                                .name(photo.getName())
                                .type(photo.getType())
                                .photoUrl(photo.getPhotoUrl())
                                .build())
                        .collect(Collectors.toSet()))
                .price(dish.getPrice().floatValue())
                .restaurantId(dish.getRestaurant().getUuid())
                .build();
    }

    public static Set<VwDish> toCreateDishSetFrom(Set<Dish> dishes) {
        log.trace("toCreateDishSetFrom {}", dishes);
        return dishes.stream().map(DishMapper::toCreateDishFrom).collect(Collectors.toSet());
    }




}
