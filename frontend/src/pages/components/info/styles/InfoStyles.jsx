import styled from "styled-components";

const InfoContentWrap = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;

  padding: 20px 10px;
  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  cursor: pointer;
`;

const InfoContentGroup = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  padding: 6px 20px;
`;

const InfoTitle = styled.h3`
  font-size: 21px;
  letter-spacing: 1.2pt;
  text-transform: capitalize;
  text-decoration: underline;
  text-underline-offset: 0.3rem;
  padding: 6px 10px;
`;

const InfoLabel = styled.label`
  font-size: 16px;
  letter-spacing: 1.2pt;
  text-transform: capitalize;
  padding: 6px 0 3px 0;
`;

const InfoData = styled.span`
  font-size: 30px;
  font-weight: 600;
  margin: 6px 0;
`;

const InfoChartTitle = styled.h3`
  font-size: 16px;
  letter-spacing: 1.2pt;
  text-transform: capitalize;
  text-decoration: underline;
  text-underline-offset: 0.3rem;
  padding: 10px 20px;
`;

export {
  InfoContentWrap,
  InfoContentGroup,
  InfoTitle,
  InfoLabel,
  InfoData,
  InfoChartTitle,
};
