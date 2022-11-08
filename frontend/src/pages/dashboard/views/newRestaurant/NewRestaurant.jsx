import React from "react";
import { useDispatch } from "react-redux";
import { registerRestaurant } from "../../../../redux/resources/restaurantResource";
import { DBSectionHeader } from "../styles/layoutStyles";

import {
  Form,
  FlexGroup,
  FlexItem,
  Label,
  Input,
  SubmitButton,
} from "../styles/RestaurantStyle";

export const NewRestaurant = () => {
  const dispatch = useDispatch();

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const dataValues = Object.fromEntries(data);

    registerRestaurant(dispatch, {
      data: JSON.stringify(dataValues),
      license: document.querySelector("#license").files[0],
      image: document.querySelector("#image").files[0],
    });

    // e.target.reset();
  };

  return (
    <>
      <DBSectionHeader>
        <h3> New Restaurant </h3>
      </DBSectionHeader>
      <Form
        onSubmit={handleSubmit}
        id="registrationForm"
        encType="multipart/form-data"
      >
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
          <FlexItem>
            <Label>phone</Label>
            <Input
              required
              maxLength={10}
              name={"phone"}
              type="tel"
              placeholder="555-555-5555"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>category: </Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"category"}
              type="text"
              placeholder="Jackson"
            />
          </FlexItem>
          <FlexItem>
            <Label>description:</Label>
            <Input
              minLength={4}
              maxLength={254}
              name={"description"}
              type="text"
              placeholder="janedoe@restaurant.io"
            />
          </FlexItem>
        </FlexGroup>

        <FlexGroup>
          <FlexItem>
            <Label>address1: </Label>
            <Input
              minLength={1}
              maxLength={50}
              name={"address1"}
              type="text"
              placeholder="111 jackson street"
            />
          </FlexItem>
          <FlexItem>
            <Label>address2: </Label>
            <Input name={"address2"} type="text" placeholder="block 1" />
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
              placeholder="city of dreams"
            />
          </FlexItem>
          <FlexItem>
            <Label>State</Label>
            <Input
              minLength={1}
              maxLength={50}
              name={"state"}
              type="text"
              placeholder="anyState"
            />
          </FlexItem>
          <FlexItem>
            <Label>Zip</Label>
            <Input
              minLength={5}
              maxLength={9}
              name={"zip"}
              type="text"
              placeholder="55555"
            />
          </FlexItem>
          <FlexItem>
            <Label>Country</Label>
            <Input
              minLength={1}
              maxLength={10}
              name={"country"}
              type="text"
              placeholder="USA"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>License</Label>
            <Input
              required
              id="license"
              name={"license"}
              type="file"
              placeholder="accepts .pdf, .doc or .docx"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <Label>Image</Label>
            <Input
              id="image"
              name={"image"}
              type="file"
              placeholder="accepts .png, .jpeg"
            />
          </FlexItem>
        </FlexGroup>
        <FlexGroup>
          <FlexItem>
            <SubmitButton>add new </SubmitButton>
          </FlexItem>
        </FlexGroup>
      </Form>
    </>
  );
};
