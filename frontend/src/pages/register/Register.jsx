import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";

// import styledComponents to use for this document
import {
    Container,
    Wrapper,
    Title,
    Form,
    Input,
    InputFull,
    Agreement,
    Button,
    LinkTo,
    Error
} from "./RegisterStyles";


// create an arrow functional component for registration
const Register = () => {
    const [inputs, setInputs] = useState({
        username: "",
        firstName: "",
        lastName: "",
        password: "",
        confirmPassword: "",
        hasAcceptedTerms: false
    }); // set state for form inputs
    const [error, setError] = useState(false); // set state for handling errors
    const [errorMessage, setErrorMessage] = useState("");

    //  Returns an imperative method for changing the location. Used by <Link>s, but
    //  may also be used by other elements to change the location
    const navigate = useNavigate();

    // validation for matching passwords
    const hasMatchingPasswords = () => {
        if (inputs.password !== inputs.confirmPassword) {
            setErrorMessage("Passwords do not match");
            setError(true); // passwords do not match, set error message to true 
            return;
        }
        setErrorMessage("");
        return true;
    }

    // handle form submit with arrow function to bind event to this object
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (hasMatchingPasswords()) {
            try {
                console.log("Persisting the user");
                await axios.post("/auth/register", inputs);
                navigate("/login");
                setErrorMessage("");
                setError(false);
            } catch (err) {
                console.log(err);
                setError(true)
                setErrorMessage(err.response.data.message)
            }
        }
    }

    const handleChange = (e) => {

        setInputs((prev) => ({ ...prev, [e.target.name]: "" + [e.target.value] }))
    }

    const handleAcceptTerms = (e) => {
        // handle true false input of terms, if false, make true, if true, make false
        setInputs((prev) => ({ ...prev, hasAcceptedTerms: !prev.hasAcceptedTerms }));
    }

    return (
        <Container>
            <Wrapper>
                <Title>CREATE AN ACCOUNT</Title>
                <Form onSubmit={handleSubmit}>
                    <InputFull
                        required
                        minLength={4}
                        maxLength={20}
                        name={"username"}
                        type={"email"}
                        onChange={handleChange}
                        placeholder="username" />
                    <Input
                        minLength={4}
                        maxLength={20}
                        name={"firstName"}
                        type={"text"}
                        placeholder="first name" />
                    <Input
                        minLength={4}
                        maxLength={20}
                        name={"lastName"}
                        type={"text"}
                        onChange={handleChange}
                        placeholder="last name" />
                    <Input
                        required
                        minLength={4}
                        maxLength={32}
                        name={"password"}
                        onChange={handleChange}
                        placeholder="password" />
                    <Input
                        required
                        minLength={4}
                        maxLength={32}
                        name={"confirmPassword"}
                        onChange={handleChange}
                        placeholder="confirm password" />
                    {error && <Error>{errorMessage}</Error>}
                    <Agreement>
                        <input
                            required
                            onClick={handleAcceptTerms}
                            name={"hasAcceptedTerms"}
                            type={"checkbox"} />
                        By creating an account, I consent to the processing of my personal
                        data in accordance with the <b>PRIVACY POLICY</b>
                        <Link to="/register/agreement"> VIEW AGREEMENT </Link>
                    </Agreement>
                    <LinkTo><Link to="/login"> HAVE AN ACCOUNT? LOGIN </Link></LinkTo>
                    <Button>CREATE</Button>
                </Form>
            </Wrapper>
        </Container>
    );
};

export default Register;