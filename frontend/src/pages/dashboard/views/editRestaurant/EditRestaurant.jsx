import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { editRestaurant } from "../../../../redux/resources/restaurantResource";
import { DBSectionHeader } from "../styles/layoutStyles";

import {
  Form,
  FlexGroup,
  FlexItem,
  Label,
  Input,
  SubmitButton,
} from "../styles/RestaurantStyle";

export const EditRestaurant = () => {
  const dispatch = useDispatch();
  const { id } = useParams();
  const [restaurant] = useState(
    useSelector((state) =>
      state.restaurant.restaurants?.filter((rest) => rest.id === id)
    )[0]
  );
  // const restaurants = useSelector((state) => state.restaurant.restaurants);

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const dataValues = Object.fromEntries(data); // convert into js object
    dataValues.id = id; // add restaurant id

    // console.log("dataValues = ", dataValues);
    editRestaurant(dispatch, {
      data: JSON.stringify(dataValues),
      image: document.querySelector("#image").files[0],
    });

    e.target.reset();
  };

  return (
    <>
      <DBSectionHeader>
        <h3>Edit Restaurant</h3>
      </DBSectionHeader>
      <Form
        id="editRestaurantForm"
        onSubmit={handleSubmit}
        encType="multipart/form-data"
      >
        {/* {"restaurant:" + JSON.stringify(restaurant)} */}
        <FlexGroup>
          <FlexItem>
            <Label>Name</Label>
            <Input
              required
              minLength={4}
              maxLength={60}
              name={"restaurantName"}
              type="text"
              placeholder={restaurant?.name}
            />
          </FlexItem>
          <FlexItem>
            <Label>alias: </Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"alias"}
              type="text"
              placeholder={restaurant?.name}
            />
          </FlexItem>
          <FlexItem>
            <Label>URL</Label>
            <Input
              minLength={4}
              maxLength={100}
              name={"url"}
              type="email"
              placeholder={restaurant?.url}
            />
          </FlexItem>
          <FlexItem>
            <Label>phone: </Label>
            <Input
              required
              minLength={9}
              maxLength={60}
              name={"phone"}
              type="tel"
              placeholder={restaurant?.phone}
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>Description</Label>
            <Input
              minLength={4}
              maxLength={100}
              name={"description"}
              type="text"
              placeholder={restaurant?.description}
            />
          </FlexItem>
          <FlexItem>
            <Label>Tags / Category</Label>
            <Input
              minLength={2}
              maxLength={100}
              name={"category"}
              type="text"
              placeholder={restaurant?.category}
            />
          </FlexItem>
        </FlexGroup>

        <FlexGroup>
          <FlexItem>
            <Label>address1: </Label>
            <Input
              minLength={4}
              maxLength={50}
              name={"address1"}
              type="text"
              placeholder={restaurant?.address1}
            />
          </FlexItem>
          <FlexItem>
            <Label>address2: </Label>
            <Input
              minLength={4}
              maxLength={50}
              name={"address2"}
              type="text"
              placeholder={restaurant?.address2}
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>City</Label>
            <Input
              minLength={1}
              maxLength={50}
              name={"city"}
              type="text"
              placeholder={restaurant?.city}
            />
          </FlexItem>
          <FlexItem>
            <Label>State</Label>
            <Input
              minLength={1}
              maxLength={50}
              name={"state"}
              type="text"
              placeholder={restaurant?.state}
            />
          </FlexItem>
          <FlexItem>
            <Label>Zip</Label>
            <Input
              minLength={4}
              maxLength={50}
              name={"zip"}
              type="text"
              placeholder={restaurant?.zip}
            />
          </FlexItem>
          <FlexItem>
            <Label>Country</Label>
            <Input
              minLength={1}
              maxLength={10}
              name={"country"}
              type="text"
              placeholder={restaurant?.country}
            />
          </FlexItem>
          <FlexItem>
            <Label>Image</Label>
            <Input
              minLength={1}
              maxLength={10}
              name={"image"}
              type="file"
              placeholder={restaurant?.image}
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <SubmitButton type="submit">update </SubmitButton>
          </FlexItem>
        </FlexGroup>
      </Form>
    </>
  );
};
