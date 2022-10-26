import styled from "styled-components";


// TABLE STYLES
const DataTable = styled.div`
  height: 600px;
  padding: 20px;
`

const DataTableTitle = styled.div`
  width: 100%;
  font-size: 24px;
  color: gray;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
    justify-content: space-between;
`;

const ActionCell = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
`

const EditButton = styled.div`
  padding: 2px 5px;
  border-radius: 5px;
  color: darkblue;
  border: 1px dotted rgba(0, 0, 139, 0.596);
  cursor: pointer;
`;

const DeleteButton = styled.div`
  padding: 2px 5px;
  border-radius: 5px;
  color: crimson;
  border: 1px dotted rgba(220, 20, 60, 0.6);
  cursor: pointer;
`

const Title = styled.h1`
  
`;

const Form = styled.form`
  width: 100%;
  flex-wrap: wrap;
  gap: 30px;
  justify-content: space-around;
`;


const Row = styled.div`
  display: flex;
  width: 100%;
  margin-top: 10px;
  margin-right: 20px;
`;

const Detail = styled.div`
  flex: 1;
  margin: 10px 20px 0px 30px;
`;

const Label = styled.label`
  width: 40%;
  padding: 10px 0px;
  display: flex;
  align-items: center;
  gap: 10px;
`;

const Input = styled.input`
  height: 20px;
  padding: 10px;
  font-size: larger;
  border: 1px solid gray;
  border-radius: 5px;
  width: 80%;
`;

const Radio = styled.input`
    margin-top: 15px;
`;

const RadioLabel = styled.label`
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
  z-index: 999;
`;

const Error = styled.span`
  color: red;
`;


export {
  DataTable,
  DataTableTitle,
  EditButton,
  DeleteButton,
  ActionCell,
  Title,
  Form,
  Detail,
  Row,
  Label,
  Input,
  Radio,
  RadioLabel,
  RadioOption,
  Select,
  SelectOption,
  Button,
  Error
}