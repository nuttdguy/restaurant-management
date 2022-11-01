import React from "react";
import { OverviewInfo } from "../../../components/chartInfo/ChartInfo";
import { SaleInfo } from "../../../components/info/saleInfo/SaleInfo";
import { SummaryInfo } from "../../../components/info/summaryInfo/SummaryInfo";
import styled from "styled-components";

const Container = styled.section`
  display: flex;
  flex-direction: column;
  flex: 4;
`;

const FlexWrap = styled.div`
  display: inline-flex;
  justify-content: space-evenly;
`;

const Title = styled.h2`
  height: 36px;
  padding: 6px 0 3px 0;
  text-transform: uppercase;
  letter-spacing: 1.2pt;
  color: darkblue;
  border-bottom: 1px solid gray;
`;

export const Overview = () => {
  return (
    <>
      <Container>
        <Title>overview</Title>
        <FlexWrap>
          <SaleInfo />
          <SummaryInfo />
        </FlexWrap>
        <OverviewInfo />
      </Container>
    </>
  );
};
