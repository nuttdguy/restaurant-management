import styled from "styled-components";

const DataTable = styled.div`
  padding: 20px 10px;
  /* width: 100%; */
  flex: 4;
  height: 50vh;
`;

const DeleteButton = styled.div`
  padding: 2px 5px;
  color: darkred;
  cursor: pointer;
`;

const ActionCell = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
`;

const Form = styled.form`
  margin: 20px 20px;
  padding: 30px;
  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  cursor: pointer;
`;

const FlexGroup = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  justify-content: space-between;
`;

const FlexImageGroup = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
`;

const FlexItemGroup = styled.div`
  flex: 50%;
  margin: 12px 0;
`;

const FlexItem = styled.div`
  flex: 50%;
  margin: 6px 0;
`;

const Image = styled.img`
  min-height: 200px;
  /* aspect-ratio: calc(16 / 9); */
  min-width: 80%;
  padding: 10px 10px;
  margin: 10px 0 24px;
  min-width: 200;
  min-height: 200;
  border: 3px solid black;
  background-color: white;
`;

const Label = styled.label`
  display: block;
  color: #2f2f2f;
  font-size: 0.9em;

  text-transform: capitalize;
  font-weight: 500;
  margin-bottom: 12px;
  padding-bottom: 12px;
`;

const Input = styled.input`
  width: calc(100% - 40px);
  border-top: hidden;
  border-left: hidden;
  border-right: hidden;
  border-bottom: 1px solid black;
  background-color: transparent;
  padding: 3px 10px 3px 0px;
  margin: 0 10px 20px 0;
  outline: 0;
  &::placeholder {
    color: gray;
    opacity: 0.3;
  }
  &:active {
    border-bottom: 1px solid teal;
  }
`;

const Select = styled.select`
  height: 40px;
  border-radius: 5px;
  margin-bottom: 18px;
  padding: 12px 0;
`;

const SelectOption = styled.option`
  font-size: larger;
`;

const SubmitButton = styled.button`
  width: 40%;
  height: 44px;
  border: none;
  padding: 15px 18px;
  text-transform: uppercase;
  background-color: darkblue;
  color: white;
  cursor: pointer;
`;

export {
  DataTable,
  DeleteButton,
  ActionCell,
  Form,
  FlexGroup,
  FlexItem,
  FlexImageGroup,
  FlexItemGroup,
  Image,
  Label,
  Input,
  Select,
  SelectOption,
  SubmitButton,
};
