import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip, Legend } from 'recharts';

interface CredibilityChartProps {
  data: { name: string; value: number }[];
}

const COLORS = ['#10b981', '#f59e0b', '#ef4444']; // High, Medium, Low

export default function CredibilityChart({ data }: CredibilityChartProps) {
  // Sort data to ensure High, Medium, Low order if possible
  const orderedData = [...data].sort((a, b) => {
    const order: Record<string, number> = { 'High': 1, 'Medium': 2, 'Low': 3 };
    return (order[a.name] || 99) - (order[b.name] || 99);
  });

  return (
    <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 h-96">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Credibility Analysis</h3>
      <div className="w-full h-[300px]">
        <ResponsiveContainer width="100%" height="100%">
          <PieChart>
            <Pie
              data={orderedData}
              cx="50%"
              cy="50%"
              innerRadius={60}
              outerRadius={100}
              paddingAngle={5}
              dataKey="value"
            >
              {orderedData.map((entry, index) => {
                let color = COLORS[2]; // default low
                if (entry.name === 'High') color = COLORS[0];
                else if (entry.name === 'Medium') color = COLORS[1];
                return <Cell key={`cell-${index}`} fill={color} />;
              })}
            </Pie>
            <Tooltip
              contentStyle={{ borderRadius: '8px', border: 'none', boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.1)' }}
            />
            <Legend verticalAlign="bottom" height={36} />
          </PieChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}
