import React from "react";

import {
  InfoContentWrap,
  InfoContentGroup,
  InfoTitle,
  InfoLabel,
  InfoData,
} from "../styles/InfoStyles";

export const SummaryInfo = () => {
  return (
    <InfoContentWrap>
      <InfoTitle>Summary:</InfoTitle>
      <InfoContentGroup>
        <InfoLabel>Dish Items:</InfoLabel>
        <InfoData> 20 </InfoData>
      </InfoContentGroup>
      <InfoContentGroup>
        <InfoLabel>Most popular </InfoLabel>
        <InfoData> Steak & Eggs </InfoData>
      </InfoContentGroup>
      <InfoContentGroup>
        <InfoLabel>Total % of revenue</InfoLabel>
        <InfoData> 23.754%</InfoData>
      </InfoContentGroup>
    </InfoContentWrap>
  );
};
