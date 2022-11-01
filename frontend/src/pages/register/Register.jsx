import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register, clearApiError } from "../../api/authResource";
import { useDispatch, useSelector } from "react-redux";

import {
  Container,
  Wrapper,
  Title,
  FlexWrap,
  Form,
  Input,
  Label,
  Agreement,
  Button,
  LinkTo,
  Error,
} from "./RegisterStyles";

// create an arrow functional component for registration
export const Register = () => {
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [inputs, setInputs] = useState({
    username: "",
    firstName: "",
    lastName: "",
    password: "",
    confirmPassword: "",
    terms: false,
  });

  const { isSuccess, isApiError, apiErrorMessage } = useSelector(
    (state) => state.userAuth
  );

  useEffect(() => {
    handleError(isApiError, apiErrorMessage);
  }, [isApiError, apiErrorMessage, dispatch]);

  useEffect(() => {
    if (isSuccess === true) {
      navigate("/login");
    }
    clearApiError(dispatch);
  }, [navigate, dispatch, isSuccess]);

  const handleChange = (e) => {
    setInputs((prev) => ({ ...prev, [e.target.name]: "" + [e.target.value] }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (inputs.password !== inputs.confirmPassword) {
      handleError(true, "Passwords do not match");
      return;
    }
    register(dispatch, inputs);
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
        <FlexWrap>
          <Title>create account</Title>
          <Form onSubmit={handleSubmit}>
            <Label>email: </Label>
            <Input
              required
              minLength={4}
              maxLength={60}
              name={"username"}
              type={"email"}
              onChange={handleChange}
              placeholder="alice@restaurant.iox "
            />
            <Label>first name: </Label>
            <Input
              minLength={2}
              maxLength={20}
              name={"firstName"}
              type={"text"}
              onChange={handleChange}
              placeholder="alice"
            />
            <Label>last name: </Label>
            <Input
              minLength={2}
              maxLength={20}
              name={"lastName"}
              type={"text"}
              onChange={handleChange}
              placeholder="jones"
            />
            <Label>password: </Label>
            <Input
              required
              minLength={4}
              maxLength={32}
              name={"password"}
              onChange={handleChange}
              placeholder="password"
            />
            <Label>confirm password: </Label>
            <Input
              required
              minLength={4}
              maxLength={32}
              name={"confirmPassword"}
              onChange={handleChange}
              placeholder="confirm password"
            />
            {error && <Error>{errorMessage}</Error>}
            <Agreement>
              <input
                style={{ padding: "12px", marginRight: "12px" }}
                required
                onClick={handleChange}
                name={"terms"}
                type={"checkbox"}
              />
              By creating an account, I consent to the processing of my personal
              data in accordance with the <b>PRIVACY POLICY</b>
              <Link to="/agreement" style={{ paddingLeft: "6px" }}>
                VIEW AGREEMENT
              </Link>
            </Agreement>
            <FlexWrap>
              <LinkTo>
                <Link to="/login"> HAVE AN ACCOUNT? LOGIN </Link>
              </LinkTo>
              <Button>CREATE</Button>
            </FlexWrap>
          </Form>
        </FlexWrap>
      </Wrapper>
    </Container>
  );
};
