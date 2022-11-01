import React, { useState } from "react";

import {
  Wrapper,
  MenuTitle,
  Form,
  FlexGroup,
  FlexItem,
  Label,
  Input,
  SubmitButton,
} from "../styles/DishStyle";

export const EditDish = () => {
  // const dishes = useSelector((state) => state.dish.dishes);

  return (
    <Wrapper>
      <MenuTitle>New Dish</MenuTitle>
      <Form id="registrationForm" encType="multipart/form-data">
        <FlexGroup>
          <FlexItem>
            <Label>Name</Label>
            <Input
              required
              minLength={4}
              maxLength={60}
              name={"restaurantName"}
              type="text"
              placeholder="Jane Doe Restaurant"
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
              placeholder="Jackson"
            />
          </FlexItem>
          <FlexItem>
            <Label>URL</Label>
            <Input
              minLength={4}
              maxLength={100}
              name={"url"}
              type="email"
              placeholder="janedoe@restaurant.io"
            />
          </FlexItem>
        </FlexGroup>

        <FlexGroup>
          <FlexItem>
            <Label>Image</Label>
            <Input
              id="license"
              name={"image"}
              type="file"
              placeholder="accepts .pdf, .doc or .docx"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <SubmitButton type="submit">add new </SubmitButton>
          </FlexItem>
        </FlexGroup>
      </Form>
    </Wrapper>
  );
};
