
import React from "react";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";
import axiosInstance from "../helper/axios";

const COLORS = ["#8884d8", "#82ca9d", "#ffc658", "#ff8042", "#00C49F"];

class ExpenseChart extends React.Component {
  state = {
    chartData: []
  };

  componentDidMount() {
    const { userId, month, year } = this.props;
    axiosInstance
      .get("/expenses/chart/category", {
        params: { userId, month, year }
      })
      .then((res) => {
        this.setState({ chartData: res.data });
      })
      .catch((err) => {
        console.error("Failed to load chart data", err);
      });
  }

  render() {
    const { chartData } = this.state;
    if (chartData.length === 0) return <p>No data available for this month.</p>;

    return (
      <div className="d-flex justify-content-center mt-4">
        <PieChart width={400} height={300}>
          <Pie
            data={chartData}
            dataKey="total"
            nameKey="category"
            cx="50%"
            cy="50%"
            outerRadius={100}
            label
          >
            {chartData.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
          <Tooltip />
          <Legend />
        </PieChart>
      </div>
    );
  }
}

export default ExpenseChart;
