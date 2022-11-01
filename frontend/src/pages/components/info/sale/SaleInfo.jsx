import React from "react";
import { ArrowDownward, ArrowUpward } from "@mui/icons-material";

import {
  FlexItemWrapper,
  FlexItem,
  Header,
  Title,
  SaleData,
  SaleDataIcon,
} from "./SaleInfoStyle";

export const SaleInfo = () => {
  return (
    <>
      <FlexItemWrapper>
        <Header>Sales:</Header>
        <FlexItem>
          {/* <SaleDataIcon> */}
          <Title>current revenue:</Title>
          <SaleData> $300.00</SaleData> <ArrowDownward />
          {/* </SaleDataIcon> */}
        </FlexItem>
        <FlexItem>
          {/* <SaleDataIcon> */}
          <Title>last month revenue:</Title>
          <SaleData> $400.00</SaleData> <ArrowUpward />
          {/* </SaleDataIcon> */}
        </FlexItem>
        <FlexItem>
          {/* <SaleDataIcon> */}
          <Title>Difference revenue:</Title>
          <SaleData> $100.00</SaleData> <ArrowDownward />
          {/* </SaleDataIcon> */}
        </FlexItem>
      </FlexItemWrapper>
    </>
  );
};
