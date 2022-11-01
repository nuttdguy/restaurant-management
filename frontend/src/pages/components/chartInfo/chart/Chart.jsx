import "./chart.css";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

export default function Chart({ title, data, dataKey, grid }) {
  return (
    <div className="chart">
      <h3 className="chartTitle">{title}</h3>

      <ResponsiveContainer width="100%" aspect={2 / 1}>
        <BarChart width={150} height={40} data={data}>
          <Bar dataKey="uv" fill="#82ca9d" />
          <Tooltip />
          <Legend />
          <YAxis />
          <XAxis dataKey="month" />
        </BarChart>
        {/* <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="pv" fill="#8884d8" />
          <Bar dataKey="uv" fill="#82ca9d" /> */}
        {/* </BarChart> */}
      </ResponsiveContainer>
    </div>
  );
}
