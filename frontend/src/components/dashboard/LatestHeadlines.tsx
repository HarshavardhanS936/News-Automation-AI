import React from 'react';

interface Headline {
  id: string;
  title: string;
  sentiment: string;
  time: string;
  source: string;
}

interface LatestHeadlinesProps {
  headlines: Headline[];
}

export default function LatestHeadlines({ headlines }: LatestHeadlinesProps) {
  const getSentimentColor = (sentiment: string) => {
    switch (sentiment.toUpperCase()) {
      case 'POSITIVE':
        return 'bg-green-100 text-green-800';
      case 'NEGATIVE':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 w-full">
      <div className="flex items-center justify-between mb-6">
        <h3 className="text-lg font-semibold text-gray-900">Latest Processed Headlines</h3>
        <button className="text-sm font-medium text-blue-600 hover:text-blue-700">View all</button>
      </div>
      <div className="flex flex-col space-y-4">
        {headlines.map((item) => (
          <div key={item.id} className="flex items-center justify-between p-4 hover:bg-gray-50 rounded-xl transition-colors border border-transparent hover:border-gray-100 cursor-pointer">
            <div className="flex flex-col">
              <span className="text-sm font-medium text-gray-900">{item.title}</span>
              <div className="flex items-center mt-1 space-x-2">
                <span className="text-xs text-gray-500">{item.source}</span>
                <span className="text-xs text-gray-300">&bull;</span>
                <span className="text-xs text-gray-500">{item.time}</span>
              </div>
            </div>
            <div className="ml-4 flex-shrink-0">
              <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getSentimentColor(item.sentiment)}`}>
                {item.sentiment}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
