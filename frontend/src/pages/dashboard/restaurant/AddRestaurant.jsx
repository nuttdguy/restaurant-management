import axios from 'axios';
import React, { useState } from 'react';


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


const inputsMemo = {
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
  license: ""
}

const AddRestaurant = () => {
  let [inputs, setInputs] = useState(inputsMemo)
  let [currentUser] = useState(JSON.parse(localStorage.getItem("user")));

  let [error, setError] = useState(false);
  let [errorMessage, setErrorMessage] = useState("");


  const handleChange = (e) => {
    setInputs((previous) => ({ ...previous, [e.target.name]: e.target.value }))
  }

  const isValidFileType = () => {
    if (inputs.license == "") {
      return false;
    }

    let ext = inputs.license.split(".");
    ext = ext[ext.length - 1];
    console.log("ext == " + ext);
    if (ext === "pdf" || ext === "doc" || ext === "docx") {
      return true;
    }

    setErrorMessage(() => "File type must be .pdf, .doc or .docx type")
    setError(() => true);

    setTimeout(() => {
      setErrorMessage(() => "");
      setError(() => true);
    }, 2000)

    return false;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (isValidFileType()) {
      try {
        // set the owner / user for creating restaurant
        inputs.username = currentUser.username;

        console.log(e.target);
        const res = await axios.postForm("/restaurant/create", {
          data: JSON.stringify(inputs),
          file: document.querySelector('#license').files[0]
        });

      } catch (err) {
        // setErrorMessage(err.response.data.message)
        console.log(err)
        setError(true)
      }
    }
  }


  return (
    <>
      <Title>New Restaurant</Title>
      {error && <Error>{errorMessage}</Error>}
      <Form id='registrationForm' encType="multipart/form-data" onSubmit={handleSubmit}>

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
              minLength={1}
              maxLength={10}
              name={"country"}
              onChange={handleChange}
              type="text"
              placeholder="USA" />
          </Detail>
          <Detail>
            <Label>License</Label>
            <Input
              id='license'
              required
              name={"license"}
              onChange={handleChange}
              type="file"
              placeholder="accepts .pdf, .doc or .docx" />
          </Detail>
        </Row>

        <Row>
          <Detail>
            <Button type="submit" >CREATE </Button>
          </Detail>
        </Row>
      </Form>
    </>
  )
}

export default AddRestaurant;