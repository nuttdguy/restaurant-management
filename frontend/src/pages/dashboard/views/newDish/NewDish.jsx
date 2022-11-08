import React, { useEffect, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { newDish } from "../../../../redux/resources/dishResource";
import { getRestaurants } from "../../../../redux/resources/restaurantResource";
import { DBSectionHeader } from "../styles/layoutStyles";

import {
  Form,
  FlexGroup,
  FlexItem,
  FlexImageGroup,
  FlexItemGroup,
  Image,
  Label,
  Input,
  Select,
  SelectOption,
  SubmitButton,
} from "../styles/DishStyle";

export const NewDish = () => {
  const [image, setValues] = useState({
    imagePreviewUrl: "",
    imageFile: null,
  });

  const dispatch = useDispatch();
  const restaurants = useSelector((state) => state.restaurant?.restaurants);
  const user = useSelector((state) => state.userAuth.currentUser);

  useEffect(() => {
    getRestaurants(dispatch);
  }, [dispatch]);

  const handleImageChange = (e) => {
    const reader = new FileReader();
    const file = e.target.files[0];

    console.log("reader ", reader);
    console.log("file ", file);
    reader.onloadend = () => {
      setValues({ ...image, imageFile: file, imagePreviewUrl: reader.result });
    };

    reader.readAsDataURL(file);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // convert form data into js object
    const data = new FormData(e.target);
    const dataValues = Object.fromEntries(data);
    dataValues.username = user.username;

    newDish(dispatch, {
      data: JSON.stringify(dataValues),
      image: image.imageFile,
    });

    // e.target.reset();s
  };

  return (
    <>
      <DBSectionHeader>
        <h3>New Dish</h3>
      </DBSectionHeader>

      <Form
        onSubmit={handleSubmit}
        id="newDishForm"
        encType="multipart/form-data"
      >
        <FlexGroup>
          <FlexItemGroup>
            {image.imageFile === null ? (
              <Image />
            ) : (
              <Image src={image.imagePreviewUrl} alt="..." />
            )}
            <Input
              id="image"
              type="file"
              accept="image/*"
              onChange={handleImageChange}
            />
          </FlexItemGroup>
          <FlexItemGroup>
            {/* <FlexItemColumn> */}
            <Label>dish name</Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"dishName"}
              type="text"
              placeholder="Fire Beans"
            />
            <Label>select restaurant:</Label>
            <Select id="phone" name={"phone"}>
              {restaurants?.map((restaurant, index) => {
                return (
                  <SelectOption name={"phone"} key={restaurant.id}>
                    {restaurant.phone}{" "}
                  </SelectOption>
                );
              })}
            </Select>
            <Label>price </Label>
            <Input
              required
              minLength={1}
              name={"price"}
              type="text"
              placeholder="2.99"
            />
            <Label>category: </Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"category"}
              type="text"
              placeholder="Beans"
            />
            {/* </FlexItemColumn> */}
          </FlexItemGroup>
        </FlexGroup>
        <FlexGroup>
          <FlexItemGroup>
            <Label>description:</Label>
            <Input
              minLength={4}
              maxLength={254}
              name={"description"}
              type="text"
              placeholder="Roasted beans in a fire sauce"
            />
          </FlexItemGroup>
        </FlexGroup>
        <FlexGroup>
          <FlexItemGroup>
            <Label>ingredients:</Label>
            <Input
              minLength={4}
              maxLength={254}
              name={"ingredients"}
              type="text"
              placeholder="Beans, red pepper, salt, water, chili powder"
            />
          </FlexItemGroup>
        </FlexGroup>

        <FlexGroup>
          <FlexItem>
            <SubmitButton type="submit">create </SubmitButton>
          </FlexItem>
        </FlexGroup>
      </Form>
    </>
  );
};
