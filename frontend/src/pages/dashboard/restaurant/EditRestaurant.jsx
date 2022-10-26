import axios from 'axios';
import React, { useState } from 'react'


import {
  Title,
  Form,
  Detail,
  Row,
  Label,
  Input,
  Button,
  Error
} from "./RestaurantStyles";


const userMemo = {
  id: "",
  restaurantName: "",
  url: "",
  category: "",
  description: "",
  address1: "",
  address2: "",
  city: "",
  state: "",
  zip: "",
  phone: "",
  country: "",
  image: ""
}


const EditRestaurant = () => {
  const [inputs, setInputs] = useState(userMemo)
  const [user] = useState(JSON.parse(localStorage.getItem("user")));

  const [error, setError] = useState(false); // controlled error object for handing errors 
  const [errorMessage, setErrorMessage] = useState("");


  // let param = useParams();  // hook to extract param

  const handleChange = (e) => {
    setInputs((previous) => ({ ...previous, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      inputs.username = user.username;
      inputs.id = "40ea837d-fa9e-4dba-9422-f10f22f18cf1";
      await axios.put("/restaurant/edit", inputs);

    } catch (err) {
      setError(true)
      setErrorMessage(err.response.data.message)
    }
  }



  return (
    <>
      <Title>Edit Restaurant</Title>
      {error && <Error>{errorMessage}</Error>}
      <Form onSubmit={handleSubmit}>

        <Row>
          <Detail>
            <Label>Name</Label>
            <Input
              required
              minLength={4}
              maxLength={60}
              name={"restaurantName"}
              onChange={handleChange}
              type="text"
              placeholder="Jane Doe Restaurant" />
          </Detail>

          <Detail>
            <Label>URL</Label>
            <Input
              required
              minLength={4}
              maxLength={100}
              name={"url"}
              onChange={handleChange}
              type="email"
              placeholder="janedoe@restaurant.io" />

          </Detail>
        </Row>

        <Row>
          <Detail>
            <Label>Category</Label>
            <Input
              required
              minLength={4}
              maxLength={30}
              name={"category"}
              onChange={handleChange}
              type="text"
              placeholder="eatery - sitdown" />
          </Detail>

          <Detail>
            <Label>Desc/Caption</Label>
            <Input
              required
              minLength={4}
              maxLength={255}
              name={"description"}
              onChange={handleChange}
              type="text"
              placeholder="We sell fresh and unique burgers" />

          </Detail>
        </Row>

        <Row>
          <Detail>
            <Label>Address 1</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"address1"}
              onChange={handleChange}
              type="text"
              placeholder="111 jane doe street" />
          </Detail>

          <Detail>
            <Label>Address 2</Label>
            <Input
              minLength={4}
              maxLength={50}
              name={"address2"}
              onChange={handleChange}
              type="text"
              placeholder="block 1" />

          </Detail>
        </Row>
        <Row>
          <Detail>
            <Label>City</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"city"}
              onChange={handleChange}
              type="text"
              placeholder="city of dreams" />
          </Detail>
          <Detail>
            <Label>State</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"state"}
              onChange={handleChange}
              type="text"
              placeholder="anyState" />
          </Detail>
        </Row>

        <Row>
          <Detail>
            <Label>Zip</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"zip"}
              onChange={handleChange}
              type="text"
              placeholder="55555" />
          </Detail>
          <Detail>
            <Label>Phone</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"phone"}
              onChange={handleChange}
              type="tel"
              placeholder="000-000-0000" />
          </Detail>
        </Row>


        <Row>
          <Detail>
            <Label>Country</Label>
            <Input
              required
              minLength={4}
              maxLength={50}
              name={"country"}
              onChange={handleChange}
              type="text"
              placeholder="USA" />
          </Detail>
        </Row>
        <Row>
          <Detail>
            <Button type="submit" >EDIT </Button>
          </Detail>
        </Row>
      </Form>
    </>
  )
}

export default EditRestaurant