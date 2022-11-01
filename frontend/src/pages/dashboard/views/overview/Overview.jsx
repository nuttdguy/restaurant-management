import React from "react";
import { OverviewInfo } from "../../../components/info/overview/OverviewInfo";
import { SaleInfo } from "../../../components/info/sale/SaleInfo";
import { SummaryInfo } from "../../../components/info/summary/SummaryInfo";
import styled from "styled-components";

const Container = styled.section`
  border: 1px solid gray;
  flex: 4;
`;

const FlexWrap = styled.div`
  border: 1px solid gray;
  display: flex;
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
