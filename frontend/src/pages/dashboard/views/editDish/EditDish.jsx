import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { editDish } from "../../../../redux/resources/dishResource";
import { DBContentInfoWrap, DBSectionHeader } from "../styles/layoutStyles";

import {
  Form,
  FlexGroup,
  FlexItem,
  Label,
  Input,
  SubmitButton,
} from "../styles/DishStyle";

export const EditDish = () => {
  const dispatch = useDispatch();
  const { id } = useParams();
  const [dish] = useState(
    useSelector((state) =>
      state.dish.dishes?.filter((dish) => dish.id === id)
    )[0]
  );

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const dataValues = Object.fromEntries(data); // convert into js object
    dataValues.id = id; // add restaurant id

    // console.log("dataValues = ", dataValues);
    editDish(dispatch, {
      data: JSON.stringify(dataValues),
      image: document.querySelector("#image").files[0],
    });

    e.target.reset();
  };

  return (
    <>
      <DBSectionHeader>
        <h3>Edit Dish</h3>
      </DBSectionHeader>
      <Form
        id="editDishForm"
        onSubmit={handleSubmit}
        encType="multipart/form-data"
      >
        <FlexGroup>
          <FlexItem>
            <Label>Name</Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"name"}
              type="text"
              value={dish?.name}
              placeholder="Steak and eggs"
            />
          </FlexItem>
          <FlexItem>
            <Label>price </Label>
            <Input
              required
              minLength={1}
              maxLength={60}
              name={"price"}
              type="text"
              value={dish?.price}
              placeholder="Jackson"
            />
          </FlexItem>
          <FlexItem>
            <Label>description</Label>
            <Input
              minLength={1}
              maxLength={100}
              name={"description"}
              type="text"
              value={dish?.description}
              placeholder="Stealk and eggs"
            />
          </FlexItem>
          <FlexItem>
            <Label>ingredients</Label>
            <Input
              minLength={1}
              maxLength={100}
              name={"ingredients"}
              type="text"
              value={dish?.ingredients}
              placeholder="beef, eggs, salt"
            />
          </FlexItem>
        </FlexGroup>

        <FlexGroup>
          <FlexItem>
            <Label>photo</Label>
            <Input
              id="photos"
              name={"photo"}
              type="file"
              placeholder="accepts .png, .jpg, .jpeg"
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
