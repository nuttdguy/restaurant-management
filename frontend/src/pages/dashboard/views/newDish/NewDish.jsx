import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { newDish } from "../../../../redux/resources/dishResource";
import { getRestaurants } from "../../../../redux/resources/restaurantResource";

import {
  Wrapper,
  MenuTitle,
  Form,
  FlexGroup,
  FlexItem,
  Label,
  Input,
  Select,
  SelectOption,
  SubmitButton,
} from "../styles/DishStyle";

export const NewDish = () => {
  const dispatch = useDispatch();
  const restaurants = useSelector((state) => state.restaurant?.restaurants);

  useEffect(() => {
    const username = JSON.parse(localStorage.getItem("user"))?.username;
    getRestaurants(dispatch, username);
  }, [dispatch]);

  const handleSubmit = (e) => {
    e.preventDefault();

    // convert form data into js object
    const data = new FormData(e.target);
    const dataValues = Object.fromEntries(data);

    // extract user from local store
    dataValues.username = JSON.parse(localStorage.getItem("user")).username;

    newDish(dispatch, {
      data: JSON.stringify(dataValues),
      image: document.querySelector("#image").files[0],
    });

    e.target.reset();
  };

  return (
    <Wrapper>
      <MenuTitle>New Dish</MenuTitle>
      <Form
        onSubmit={handleSubmit}
        id="newDishForm"
        encType="multipart/form-data"
      >
        <FlexGroup>
          <FlexItem>
            <Label>dish name</Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"dishName"}
              type="text"
              placeholder="Fire Beans"
            />
          </FlexItem>
          {/* {JSON.stringify(restaurants)} */}
          <FlexItem>
            <Label>select restaurant:</Label>
            <Select id="restaurantId" name={"restaurantId"}>
              {restaurants === null
                ? null
                : restaurants?.map((restaurant) => {
                    return (
                      <SelectOption
                        name={"restaurantId"}
                        key={restaurant.id}
                        id={restaurant.id}
                      >
                        {restaurant.id}{" "}
                      </SelectOption>
                    );
                  })}
            </Select>
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>price </Label>
            <Input
              required
              minLength={1}
              name={"price"}
              type="text"
              placeholder="2.99"
            />
          </FlexItem>
          <FlexItem>
            <Label>category: </Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"category"}
              type="text"
              placeholder="Beans"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>description:</Label>
            <Input
              minLength={4}
              maxLength={254}
              name={"description"}
              type="text"
              placeholder="Roasted beans in a fire sauce"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>ingredients:</Label>
            <Input
              minLength={4}
              maxLength={254}
              name={"ingredients"}
              type="text"
              placeholder="Beans, red pepper, salt, water, chili powder"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>image:</Label>
            <Input
              id="image"
              name={"image"}
              type="file"
              placeholder="accepts .png, .jpeg, .svg"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <SubmitButton type="submit">create </SubmitButton>
          </FlexItem>
        </FlexGroup>
      </Form>
    </Wrapper>
  );
};
