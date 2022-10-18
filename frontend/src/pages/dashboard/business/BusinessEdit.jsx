import React from 'react'

import {
  Title,
  Form,
  Detail,
  Label,
  Input,
  Button
} from "./BusinessStyles";


const BusinessEdit = () => {
  return (
    <>
      <Title>Business</Title>
      <Form >
        <Detail>
          <Label>First Name</Label>
          <Input type="text" placeholder="Jane" />
        </Detail>
        <Detail>
          <Label>Last Name</Label>
          <Input type="text" placeholder="Doe" />
        </Detail>

        <Detail>
          <Label>Business Name</Label>
          <Input type="text" placeholder="Jane Doe Restaurant" />
        </Detail>
        <Detail>
          <Label>Business URL</Label>
          <Input type="text" placeholder="janedoe@restaurant.io" />
        </Detail>

        <Detail>
          <Label>Business Category</Label>
          <Input type="text" placeholder="eatery - sitdown" />
        </Detail>
        <Detail>
          <Label>Business Desc/Caption</Label>
          <Input type="text" placeholder="We sell fresh and unique burgers" />
        </Detail>

        <Detail>
          <Label>Address 1</Label>
          <Input type="text" placeholder="111 jane doe street" />
        </Detail>
        <Detail>
          <Label>Address 2</Label>
          <Input type="text" placeholder="block 1" />
        </Detail>

        <Detail>
          <Label>Business City</Label>
          <Input type="text" placeholder="city of dreams" />
        </Detail>
        <Detail>
          <Label>Business State</Label>
          <Input type="text" placeholder="anyState" />
        </Detail>


        <Detail>
          <Label>Business Zip</Label>
          <Input type="text" placeholder="55555" />
        </Detail>
        <Detail>
          <Label>Business Phone</Label>
          <Input type="text" placeholder="000-000-0000" />
        </Detail>


        <Detail>
          <Label>Business Country</Label>
          <Input type="text" placeholder="USA" />
        </Detail>

        <Detail>
          <Label>Username</Label>
          <Input type="text" placeholder="janedoe@restaurant.io" />
        </Detail>
        <Detail>
          <Label>Change Password</Label>
          <Input type="password" placeholder="password" />
        </Detail>

        <Detail>
          <Button>Submit</Button>
        </Detail>

      </Form>
    </>
  )
}

export default BusinessEdit