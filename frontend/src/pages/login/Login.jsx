import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";
// import { AuthContext } from "../api/AuthContext";

import {
  Container,
  Wrapper,
  Title,
  Form,
  Input,
  Button,
  LinkTo,
  Error
} from "./LoginStyles";

const Login = () => {
  const [fields, setFields] = useState({
    username: "",
    password: ""
  })
  const [error, setError] = useState(false);

  const navigate = useNavigate();

  const handleOnChange = (e) => {
    setFields((previousState) => ({ ...previousState, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // await login(fields)
      navigate("/");
    } catch (err) {
      setError(err.response.data)
    }
  }


  return (
    <Container>
      <Wrapper>
        <Title>SIGN IN</Title>
        <Form onSubmit={handleSubmit}>
          <Input
            required
            type="text"
            minLength="4"
            placeholder="username"
            name="username"
            onChange={handleOnChange}
          />
          <Input
            required
            type="password"
            minLength="4"
            maxLength="20"
            placeholder="password"
            name="password"
            onChange={handleOnChange}
          />
          {error && <Error>An account exists for {fields.username} </Error>}
          <LinkTo><Link to="/register">DON'T HAVE AN ACCOUNT? REGISTER </Link></LinkTo>
          <Button type="submit" >LOGIN </Button>
        </Form>
      </Wrapper>
    </Container>
  );
}

export default Login;