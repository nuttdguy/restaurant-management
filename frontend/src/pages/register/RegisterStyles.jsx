import styled from "styled-components";

// page container
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

// center the wrap within the parent
const Wrapper = styled.section`
  display: inline-flex; // display item content inline
  flex-wrap: wrap; // wrap items of content

  padding: 24px 12px;
  width: 60%;
  max-width: 60%; // width will be 60% of parent
  background-color: white;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
`;

// set flex to align content center left
const FlexTitleGroup = styled.article`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 12px 12px;
  flex: 1; // set size ratio 1:1
`;

const Form = styled.form`
  display: flex; // establish this form as its own flex container
  flex-direction: column; // set content flow to column
  max-width: 90%; // width will be 90% of parent
  flex: 3; // size of 3:11

  padding: 12px 27px;
  border-left: 1px solid gray;
`;

const Input = styled.input`
  font-size: 1.2rem;
  margin: 0px 20px 10px 0px;
  padding: 12px;
  &::placeholder {
    opacity: 0.3;
  }
`;

const Title = styled.h1`
  font-size: 2.2rem;
  font-weight: 300;
  text-transform: uppercase;
  padding: 0px 12px;
`;

const Label = styled.label`
  font-size: 0.9rem;
  font-weight: bold;
  opacity: 0.5;
  text-transform: uppercase;
  padding: 6px 0;
`;

const Agreement = styled.span`
  font-size: 12px;
  padding: 12px 0;
`;

const Button = styled.button`
  font-size: 1.2rem;
  background-color: darkblue;
  border: none;
  color: white;
  margin: 12px 12px 12px 0;
  padding: 18px 18px 18px 0;
  cursor: pointer;
`;

const LinkTo = styled.span`
  margin-left: auto;
  padding: 12px 12px 12px 0;
  font-size: 0.8rem;
  text-decoration: underline;
  cursor: pointer;
`;

const Error = styled.span`
  font-size: small;
  color: red;
`;

export {
  Container,
  Wrapper,
  Title,
  FlexTitleGroup,
  Form,
  Input,
  Label,
  Agreement,
  Button,
  LinkTo,
  Error,
};
