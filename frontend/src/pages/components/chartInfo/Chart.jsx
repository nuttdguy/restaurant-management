import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import styled from "styled-components";

const ChartWrap = styled.article`
  margin: 20px;
  padding: 20px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
`;

const ChartTitle = styled.h3`
  margin-bottom: 20px;
`;

export default function Chart({ title, data, dataKey, grid }) {
  return (
    <ChartWrap>
      <ChartTitle>{title}</ChartTitle>

      <ResponsiveContainer width="100%" aspect={3 / 1}>
        <BarChart width={150} height={40} data={data}>
          <Bar dataKey="uv" fill="#82ca9d" />
          <Tooltip />
          <Legend />
          <YAxis />
          <XAxis dataKey="month" />
        </BarChart>
      </ResponsiveContainer>
    </ChartWrap>
  );
}
