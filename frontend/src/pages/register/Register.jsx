import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";

import {
    Container,
    Wrapper,
    Title,
    Form,
    Input,
    InputFull,
    Agreement,
    Button,
    LinkTo
} from "./RegisterStyles";


const Register = () => {
    return (
        <Container>
            <Wrapper>
                <Title>CREATE AN ACCOUNT</Title>
                <Form>
                    <Input placeholder="first name" />
                    <Input placeholder="last name" />
                    <Input placeholder="password" />
                    <Input placeholder="confirm password" />
                    <InputFull placeholder="email" />
                    <Agreement>
                        By creating an account, I consent to the processing of my personal
                        data in accordance with the <b>PRIVACY POLICY</b>
                    </Agreement>
                    <LinkTo><Link to="/login"> HAVE AN ACCOUNT? LOGIN </Link></LinkTo>
                    <Button>CREATE</Button>
                </Form>
            </Wrapper>
        </Container>
    );
};

export default Register;