import styled from "styled-components";

const Wrapper = styled.div`
  padding: 0 12px 0 20px;
  flex: 1;
  height: calc(100vh - 120px);
  background-color: rgb(251, 251, 255);
  position: sticky;
  top: 120px;
`;

const MenuTitle = styled.h3`
  margin: 3px 0px 12px 0px;
  padding: 8px 0;
  text-transform: uppercase;
  letter-spacing: 1.2pt;
  color: darkblue;
  padding-bottom: 6px;
  border-bottom: 1px solid gray;
  border-top: 1px solid gray;
`;

const MenuList = styled.ul`
  list-style: none;
  padding: 0px;
  color: black;
`;

const MenuItem = styled.li`
  margin: 4px 0px;
  padding: 0 3px;
  cursor: pointer;
  text-decoration: none;
  align-items: baseline;
  color: #0e0e0f;
  border-bottom: 1px solid transparent;
  &:hover {
    border-bottom: 1px solid darkblue;
  }
  &:active,
  &:visited {
    color: red;
  }
`;

export { Wrapper, MenuTitle, MenuList, MenuItem };
