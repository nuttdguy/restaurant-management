import styled from "styled-components";

const Container = styled.div`
  width: 100vw;
  height: 60px;
  background-color: white;
  position: sticky;
  top: 0;
  z-index: 99;
`;


const Wrapper = styled.div`
  height: 100%;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const TopLeft = styled.div`
  
`;

const TopRight = styled.div`
  display: flex;
  align-items: center;
`;

const Logo = styled.span`
  font-size: 42px;
  font-weight: bold;
  color: darkblue;
`;

const IconContainer = styled.div`
  position: relative;
  margin-right: 12px;
  color: #555;
`;

const IconBadge = styled.span`
  font-size: 12px;
  width: 18px;
  height: 18px;
  padding: 0 6px;

  position: absolute;
  top: -5px;
  right: -5px;
  background-color: red;
  color: white;
  border-radius: 50%;

  display: flex;
  align-items: center;
  justify-content: center;

`;

const IconAvatar = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
`;


export {
  Container,
  Wrapper,
  TopLeft,
  TopRight,
  Logo,
  IconContainer,
  IconBadge,
  IconAvatar
}