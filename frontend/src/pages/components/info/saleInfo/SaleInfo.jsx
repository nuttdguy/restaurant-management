import React from "react";
import { ArrowDownward, ArrowUpward } from "@mui/icons-material";

import {
  InfoContentWrap,
  InfoContentGroup,
  InfoTitle,
  InfoLabel,
  InfoData,
} from "../styles/InfoStyles";

export const SaleInfo = () => {
  return (
    <InfoContentWrap>
      <InfoTitle>Sales:</InfoTitle>
      <InfoContentGroup>
        <InfoLabel>current revenue:</InfoLabel>
        <InfoData> $300.00</InfoData> <ArrowDownward />
      </InfoContentGroup>
      <InfoContentGroup>
        <InfoLabel>last month revenue:</InfoLabel>
        <InfoData> $400.00</InfoData> <ArrowUpward />
      </InfoContentGroup>
      <InfoContentGroup>
        <InfoLabel>Difference revenue:</InfoLabel>
        <InfoData> $100.00</InfoData> <ArrowDownward />
      </InfoContentGroup>
    </InfoContentWrap>
  );
};
