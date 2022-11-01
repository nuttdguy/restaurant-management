import React from "react";
import styled from "styled-components";
import Chart from "./Chart";

const Container = styled.div`
  flex: 1 0 100%;
`;

const stats = [
  {
    month: "January",
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    month: "February",
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    month: "March",
    uv: 2000,
    pv: 9800,
    amt: 2290,
  },
  {
    month: "April",
    uv: 2780,
    pv: 3908,
    amt: 2000,
  },
  {
    month: "May",
    uv: 1890,
    pv: 4800,
    amt: 2181,
  },
  {
    month: "June",
    uv: 2390,
    pv: 3800,
    amt: 2500,
  },
];

export const ChartInfo = () => {
  return (
    <Container>
      <Chart data={stats} dataKey="Sales" title="Sales Performance" />
    </Container>
  );
};
