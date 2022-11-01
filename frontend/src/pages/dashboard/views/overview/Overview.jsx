import { AnalyticsOutlined } from "@mui/icons-material";
import React from "react";
import { ChartInfo } from "../../../components/chartInfo/ChartInfo";
import { SaleInfo } from "../../../components/info/saleInfo/SaleInfo";
import { SummaryInfo } from "../../../components/info/summaryInfo/SummaryInfo";
import { DBSectionHeader, DBContentInfoWrap } from "../styles/layoutStyles";

export const Overview = () => {
  return (
    <>
      <DBSectionHeader>
        <AnalyticsOutlined />
        <h3>overview</h3>
      </DBSectionHeader>
      <DBContentInfoWrap>
        <SaleInfo />
        <SummaryInfo />
      </DBContentInfoWrap>
      <DBContentInfoWrap>
        <ChartInfo />
      </DBContentInfoWrap>
    </>
  );
};
