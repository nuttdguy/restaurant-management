import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const Container = styled.section`
  width: 100vw;
  height: 100vh;
  background: linear-gradient(
      rgba(255, 255, 255, 0.5),
      rgba(255, 255, 255, 0.5)
    ),
    url("https://images.unsplash.com/photo-1592861956120-e524fc739696?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1740&q=80")
      center;
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  min-width: 50%;
  padding: 30px 20px 20px 20px;
  background-color: white;
`;

const Title = styled.h1`
  flex: 1;
  font-size: 36px;
  font-weight: 300;
  padding: 0px 12px;
  margin-right: 18px;
`;

const LinkContainer = styled.div`
  flex: 1;
  border-left: 1px solid gray;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
`;

const Button = styled.button`
  border: none;
  width: 110px;
  padding: 15px 18px;
  margin: 18px;
  background-color: darkblue;
  color: white;
  cursor: pointer;
`;

export const Landing = () => {
  return (
    <Container>
      <Wrapper>
        <Title>Restuarant Management</Title>

        <LinkContainer>
          <Link to="/login">
            <Button>LOGIN </Button>
          </Link>
          <Link to="/register">
            <Button>REGISTER </Button>
          </Link>
        </LinkContainer>
      </Wrapper>
    </Container>
  );
};
