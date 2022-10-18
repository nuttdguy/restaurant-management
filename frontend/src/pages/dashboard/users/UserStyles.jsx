import styled from "styled-components";

const Title = styled.h1`
  /* width: 100vw; */
`;

const Form = styled.div`
  display: flex;
  flex-wrap: wrap;
`;

const Detail = styled.div`
  width: 400px;
  margin-top: 10px;
  margin-right: 20px;
`;

const Label = styled.div`
  margin-bottom: 10px;
  font-size: larger;
  font-weight: 600;
  color: rgb(151, 150, 150);
`;

const Input = styled.input`
  height: 20px;
  padding: 10px;
  font-size: larger;
  border: 1px solid gray;
  border-radius: 5px;
`;

const Radio = styled.div`
    margin-top: 15px;
`;

const RadioLabel = styled.div`
  margin: 10px;
  font-size: 18px;
  color: #555;
`;

const RadioOption = styled.input`
  margin: 10px;
  font-size: 18px;
  color: #555;
`;

const Select = styled.select`
  height: 40px;
  border-radius: 5px;
`;

const SelectOption = styled.option`
  font-size: larger;
`;

const Button = styled.button`
  width: 40%;
  height: 44px;
  border: none;
  padding: 15px 18px;
  margin-right: 12px;
  margin-left: auto;
  background-color: darkblue;
  color: white;
  cursor: pointer;
`;

export {
  Title,
  Form,
  Detail,
  Label,
  Input,
  Radio,
  RadioLabel,
  RadioOption,
  Select,
  SelectOption,
  Button
}