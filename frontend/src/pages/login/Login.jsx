import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { login, clearApiError } from "../../api/authResource";

import {
  Container,
  Wrapper,
  FlexTitleGroup,
  Title,
  Form,
  Label,
  Input,
  Button,
  LinkTo,
  Error,
} from "./LoginStyles";

export const Login = () => {
  const { isSuccess, isApiError, apiErrorMessage } = useSelector(
    (state) => state.userAuth
  );
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [error, setError] = useState(isApiError);
  const [errorMessage, setErrorMessage] = useState(apiErrorMessage);

  useEffect(() => {
    handleError(isApiError, apiErrorMessage);
  }, [isApiError, apiErrorMessage]);

  useEffect(() => {
    if (isSuccess) {
      navigate("/restaurant", { replace: false }); // change the router location / navigate to url
    }
    clearApiError(dispatch);
  }, [navigate, dispatch, isSuccess]);

  const handleSubmit = (e) => {
    e.preventDefault();
    login(dispatch, {
      username: e.target?.username.value,
      password: e.target?.password.value,
    });
  };

  const handleError = (isError, message) => {
    setErrorMessage(message);
    setError(isError);
    setTimeout(() => {
      setErrorMessage("");
      setError(!isError);
    }, 3000);
  };

  return (
    <Container>
      <Wrapper>
        <FlexTitleGroup>
          <Title>LOGIN</Title>
        </FlexTitleGroup>
        <Form onSubmit={handleSubmit}>
          <Label>email / username: </Label>
          <Input
            required
            type="text"
            minLength="1"
            placeholder="username"
            name="username"
          />
          <Label>password: </Label>
          <Input
            required
            type="password"
            minLength="4"
            maxLength="20"
            placeholder="password"
            name="password"
          />
          {error && <Error>{errorMessage}</Error>}

          <Button type="submit">LOGIN </Button>
          <LinkTo>
            <Link to="/register">DON'T HAVE AN ACCOUNT? REGISTER </Link>
          </LinkTo>
        </Form>
      </Wrapper>
    </Container>
  );
};
