import styled from "styled-components";

const FlexItemWrapper = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;
  margin: 20px 20px;
  padding: 30px;
  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  cursor: pointer;
`;

const FlexItem = styled.div`
  margin: 6px 0px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Header = styled.h3`
  font-size: 21px;
  letter-spacing: 1.2pt;
  text-transform: uppercase;
  text-decoration: underline;
  padding: 6px 0;
`;

const Title = styled.span`
  font-size: 16px;
  letter-spacing: 1.2pt;
  text-transform: capitalize;
  padding: 6px 0 3px 0;
`;

const SaleData = styled.span`
  font-size: 30px;
  font-weight: 600;
  margin: 6px 0;
`;

const SaleDataIcon = styled.span`
  /* display: flex;
  flex-wrap: wrap;
  flex: 1;
  align-items: center; */
`;

const SaleDataSummary = styled.span`
  font-size: 15px;
  color: gray;
`;

export {
  FlexItemWrapper,
  FlexItem,
  Header,
  Title,
  SaleData,
  SaleDataIcon,
  SaleDataSummary,
};
