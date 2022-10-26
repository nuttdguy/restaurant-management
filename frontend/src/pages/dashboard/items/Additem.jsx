import axios from 'axios';
import React, { useState } from 'react'

import {
  Title,
  Form,
  Detail,
  Label,
  Input,
  Button,
  Error
} from "../restaurant/RestaurantStyles";


const initialState = {
  itemName: "",
  description: "",
  ingredients: "",
  price: "",
  images: []
}

const AddItem = () => {
  let [inputs, setInputs] = useState(initialState)
  let [user] = useState(JSON.parse(localStorage.getItem("user")));

  let [error, setError] = useState(false); // controlled error object for handing errors 
  let [errorMessage, setErrorMessage] = useState("");


  const handleChange = (e) => {
    setInputs((previous) => ({ ...previous, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      inputs.username = user.username;
      // const res = await axios.post("/restaurant/:id/items/create", inputs);
      const res = await axios.post("/restaurant/0449571c-53a0-4351-b7dc-e528dd54970e/items/create", inputs);
      setInputs(res.data);
    } catch (err) {
      setError(true)
      setErrorMessage(err.response.data.message)
    }
  }


  return (
    <>
      <Title>Add Dish</Title>
      {error && <Error>{errorMessage}</Error>}
      <Form onSubmit={handleSubmit}>
        <Detail>
          <Label>Dish Name</Label>
          <Input
            required
            minLength={4}
            maxLength={60}
            name={"itemName"}
            onChange={handleChange}
            type="text"
            placeholder="Steak and fries" />
        </Detail>
        <Detail>
          <Label>Price</Label>
          <Input
            required
            minLength={4}
            maxLength={100}
            name={"price"}
            onChange={handleChange}
            type="text"
            placeholder="8.99" />
        </Detail>
        <Detail>
          <Label>Dish Ingredients</Label>
          <Input
            required
            minLength={4}
            maxLength={100}
            name={"ingredients"}
            onChange={handleChange}
            type="text"
            placeholder="Steak, Potatos, Gravy" />
        </Detail>
        <Detail>
          <Label>Description</Label>
          <Input
            required
            minLength={4}
            maxLength={100}
            name={"description"}
            onChange={handleChange}
            type="text"
            placeholder="Steak served with fries" />
        </Detail>

        <Button type="submit" >CREATE </Button>

      </Form>
    </>
  )
}

export default AddItem;