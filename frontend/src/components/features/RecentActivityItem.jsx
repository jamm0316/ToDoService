import React from 'react';
import {MoreVertical} from 'lucide-react';

const RecentActivityItem = ({activity}) => {
  return (
    <div className="flex items-center justify-between bg-gray-50 rounded-xl p-4 hover:bg-gray-100 transition-colors cursor-pointer">
      <div className="flex items-center space-x-3">
        <div className="w-12 h-12 bg-gradient-to-r from-pink-100 to-purple-100 rounded-xl flex items-center justify-center">
          <span className="text-lg">{activity.icon}</span>
        </div>
        <div>
          <h4 className="font-semibold text-gray-800">{activity.name}</h4>
          <p className="text-sm text-gray-500">{activity.time}</p>
        </div>
      </div>
      <MoreVertical className="w-5 h-5 text-gray-400" />
    </div>
  );
};


export default RecentActivityItem;