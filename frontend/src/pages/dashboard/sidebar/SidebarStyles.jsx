import styled from "styled-components";

const MenuList = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  list-style: none;
  padding-left: 10px;
`;

const MenuItem = styled.li`
  padding: 6px 3px;
  border-bottom: 1px solid transparent;
  cursor: pointer;
  &:hover {
    border-bottom: 1px solid black;
  }
  &:active,
  &:visited {
    color: red;
  }
`;

const MenuLabel = styled.label`
  padding-left: 10px;
  color: black;
`;

export { MenuList, MenuItem, MenuLabel };
