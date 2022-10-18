import styled from "styled-components";

const Wrapper = styled.div`
  padding: 24px;
`;

const Menu = styled.div`

`;

const MenuTitle = styled.div`
  font-size: 28px;
  color: darkblue;
`;

const MenuList = styled.ul`
  list-style: none;
  padding: 6px;
`;

const MenuItem = styled.li`
  padding: 6px;
  cursor: pointer;
  border-radius: 6px;
  text-decoration: none;
  align-items: center;
  color: black;
`;

const MenuItemEvent = styled(MenuItem)`
  &:hover,
  &:active {
    background-color: rgb(240, 240, 255);
  }
`;

const MenuIcon = styled.span`
  margin-right: 6px;
  font-size: 18px;
`;

export {
  Wrapper,
  Menu,
  MenuTitle,
  MenuList,
  MenuItem,
  MenuItemEvent,
  MenuIcon
}