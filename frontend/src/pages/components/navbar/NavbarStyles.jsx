import styled from "styled-components";

const NavContainer = styled.section`
  width: 100%;
  height: 100px;
  background-color: white;
  position: sticky;
  top: 0;
  z-index: 999;
`;

const NavWrapper = styled.div`
  height: 100%;
  padding: 0px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const NavTitle = styled.h1`
  font-size: 36px;
  padding: 20px 0 20px 20px;
  text-transform: uppercase;
  letter-spacing: 2pt;
  color: darkblue;
`;

export { NavContainer, NavWrapper, NavTitle };
