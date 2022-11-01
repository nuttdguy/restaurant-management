import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 12px 0 20px;
  height: calc(100vh - 100px);

  flex: 1;
  position: sticky;
  top: 100px;
`;

const MenuTitle = styled.h3`
  display: inline-flex;
  align-items: center;
  gap: 10px;
  /* align-items: flex-start; */
  /* flex: 1; */

  /* margin: 3px 0px 12px 0px; */
  /* padding: 6px 0; */
  border-bottom: 1px solid gray;

  font-size: 1.3rem;
  color: darkblue;
  text-transform: uppercase;
  letter-spacing: 1.2pt;
  border-bottom: 1px solid gray;
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
