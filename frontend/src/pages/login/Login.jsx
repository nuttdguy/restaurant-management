import React, { useContext, useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

// import styles to use for this component
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


// functional login component
const Login = () => {
  const [inputs, setInputs] = useState({
    id: "",
    username: "",
    password: ""
  }); // controlled inputs for state
  const [error, setError] = useState(false); // controlled error object for handing errors 
  const [errorMessage, setErrorMessage] = useState("");

  // returns the imperative method for changing the location
  const navigate = useNavigate();

  // Accepts a context object (the value returned from `React.createContext`) and returns the current
  // context value, as given by the nearest context provider for the given context.
  // return the custom AuthContextProvider with login, logout and its state from the App context
  const { login } = useContext(AuthContext);

  // func to handle state changes
  const handleOnChange = (e) => {
    setInputs((previousState) => ({ ...previousState, [e.target.name]: e.target.value }))
  }

  // func to handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault(); // prevent the form from submitting
    let to = "/admin/restaurant";
    try {
      await login(inputs); // call the login method with the inputs from this form
      navigate(to); // change the router location / navigate to url 
    } catch (err) {
      setError(true)
      setErrorMessage(err.response.data.message)
    }
  }


  return (
    <Container>
      <Wrapper>
        <Title>LOGIN</Title>
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
          {error && <Error>{errorMessage}</Error>}
          <LinkTo><Link to="/register">DON'T HAVE AN ACCOUNT? REGISTER </Link></LinkTo>
          <Button type="submit" >LOGIN </Button>
        </Form>
      </Wrapper>
    </Container>
  );
}

export default Login;