import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { InfoChartTitle } from "../styles/InfoStyles";

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
    <>
      <InfoChartTitle>monthly - revenue</InfoChartTitle>
      <ResponsiveContainer width="100%" height="80%" aspect={3 / 1}>
        <BarChart
          width={150}
          height={40}
          data={stats}
          title="Sales Performance"
        >
          <Bar dataKey="uv" fill="#82ca9d" />
          <Tooltip />
          <Legend />
          <YAxis />
          <XAxis dataKey="month" />
        </BarChart>
      </ResponsiveContainer>
    </>
  );
};
