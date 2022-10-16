import React from "react";

import {
  Container,
  Title,
  Form,
  Detail,
  Label,
  Input,
  Button
} from "./UserStyles";

const User = () => {
  return (

    <Container>
      <Title>User Profile</Title>
      <Form >
        <Detail>
          <Label>Username</Label>
          <Input type="text" placeholder="jane@example.com" />
        </Detail>
        <Detail>
          <Label>First Name</Label>
          <Input type="text" placeholder="Jane" />
        </Detail>
        <Detail>
          <Label>Change Password</Label>
          <Input type="password" placeholder="password" />
        </Detail>
        <Detail>
          <Label>Last Name</Label>
          <Input type="text" placeholder="Doe" />
        </Detail>
        <Detail>
          <Button>Submit</Button>
        </Detail>
      </Form>
    </Container>
  );
}

export default User;