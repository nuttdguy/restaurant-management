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
  width: 60%; // width will be 60% of parent
  padding: 30px 20px 20px 20px;
  background-color: white;
`;

const FlexWrap = styled.div`
  gap: 10px;
  width: 100%;
  align-items: center;
  display: inline-flex; // establish items within this wrap to align in row
`;

const Title = styled.h1`
  flex-basis: 35%; // establish the base size
  flex: 1;
  text-transform: uppercase;
  font-size: 36px;
  font-weight: 300;
  padding: 0px 12px;
  margin-right: 18px;
`;

const Form = styled.form`
  flex: 3; // container size relative to parent

  display: flex; // establish this form as its own flex container
  flex-direction: column;
  padding: 12px 27px;
  border-left: 1px solid gray;
`;

const Input = styled.input`
  font-size: larger;
  margin: 0px 20px 10px 0px;
  padding: 12px;
  &::placeholder {
    opacity: 0.3;
  }
`;

const Label = styled.label`
  font-size: smaller;
  font-weight: bold;
  text-transform: uppercase;
  opacity: 0.5;
`;

const Agreement = styled.span`
  font-size: 12px;
  margin: 20px 0px;
`;

const Button = styled.button`
  flex-basis: 50%;
  border: none;
  padding: 15px 18px;
  margin-right: 20px;
  background-color: darkblue;
  color: white;
  cursor: pointer;
`;

const LinkTo = styled.span`
  flex: 1;
  width: 80%;
  margin: 5px 0px;
  font-size: 12px;
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
  FlexWrap,
  Form,
  Input,
  Label,
  Agreement,
  Button,
  LinkTo,
  Error,
};
