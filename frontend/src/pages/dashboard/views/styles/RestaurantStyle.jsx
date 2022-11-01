import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  /* padding: 0 12px 0 20px; */
  height: calc(100vh - 100px);

  flex: 4;
  position: sticky;
  top: 100px;
`;

const MenuTitle = styled.h3`
  display: flex;
  align-items: center;
  gap: 10px;

  margin: 3px 0px 12px 0px;
  padding: 0 0 3px 0;
  border-bottom: 1px solid gray;

  font-size: 1.3em;
  color: darkblue;
  text-transform: uppercase;
  letter-spacing: 1.2pt;
  border-bottom: 1px solid gray;
`;

// TABLE STYLES - RESTAURANT LIST STYLES
const DataTable = styled.div`
  display: flex;
  height: 600px;
  /* padding: 20px; */
`;

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
`;

const EditButton = styled.div`
  padding: 2px 5px;
  border-radius: 5px;
  color: darkblue;
  border: 1px dotted rgba(0, 0, 139, 0.596);
  cursor: pointer;
`;

const DeleteButton = styled.div`
  padding: 2px 5px;
  color: darkred;
  cursor: pointer;
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

const FlexItem = styled.div`
  flex: 50%;
  margin: 6px 0;
`;

const Label = styled.label`
  display: block;
  color: #2f2f2f;
  font-size: 0.9em;
  text-transform: capitalize;
  font-weight: 500;
  margin-bottom: 6px;
`;

const Input = styled.input`
  width: calc(100% - 40px);
  border-top: hidden;
  border-left: hidden;
  border-right: hidden;
  border-bottom: 1px solid black;
  background-color: transparent;
  padding: 3px 10px 3px 0px;
  margin: 0 10px 10px 0;
  outline: 0;
  &::placeholder {
    color: gray;
    opacity: 0.3;
  }
  &:active {
    border-bottom: 1px solid teal;
  }
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
  Wrapper,
  Form,
  FlexGroup,
  FlexItem,
  MenuTitle,
  Label,
  Input,
  SubmitButton,
  DataTable,
  DataTableTitle,
  ActionCell,
  EditButton,
  DeleteButton,
};

// const FlexWrapper = styled.div`
//   width: 100%;
//   display: flex;
//   justify-content: space-between;
// `;

// const TitleContainer = styled.section`
//   display: flex;
//   align-items: center;
//   justify-content: space-between;
// `;
// const Title = styled.h1`
//   font-size: 16px;
//   letter-spacing: 1.2pt;
//   text-transform: capitalize;
// `;
// const ButtonAdd = styled.button``;
// const SectionTop = styled.section``;
// const SectionTopLeft = styled.section``;
// const InfoTop = styled.div``;
// const InfoTitle = styled.span``;

// const SectionTopRight = styled.section``;

// const SectionBottom = styled.section``;

// export {
//   FlexWrapper,
//   TitleContainer,
//   Title,
//   ButtonAdd,
//   SectionTop,
//   SectionTopLeft,
//   InfoTop,
//   InfoTitle,
//   SectionTopRight,
//   SectionBottom,
// };
