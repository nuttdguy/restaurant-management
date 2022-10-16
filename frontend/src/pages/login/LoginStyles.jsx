import styled from "styled-components";

const Container = styled.div`
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
  width: 40%;
  padding: 30px 20px 20px 20px;
  background-color: white;
`;

const Title = styled.h1`
  font-size: 36px;
  font-weight: 300;
  padding: 0px 12px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  padding: 12px;
`;

const Input = styled.input`
  flex: 1;
  font-size: larger;
  min-width: 40%;
  margin: 20px 10px 0px 0px;
  padding: 12px;
`;

const Button = styled.button`
  width: 40%;
  border: none;
  padding: 15px 18px;
  margin-right: 12px;
  margin-left: auto;
  background-color: darkblue;
  color: white;
  cursor: pointer;
`;

const LinkTo = styled.p`
  width: 100%;
  margin: 12px 0px;
  margin-left: auto;
  font-size: 12px;
  text-decoration: underline;
  cursor: pointer;
`;

const Error = styled.span`
  color: red;
`;

export {
  Container,
  Wrapper,
  Title,
  Form,
  Input,
  Button,
  LinkTo,
  Error
}