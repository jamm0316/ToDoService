import React from 'react';
import { Tag, Clock, User, CheckCircle2 } from 'lucide-react';

const TaskCard = ({ task, isCompact = false }) => {
  const {
    title,
    status,
    priority,
    dueDate,
    dayLabel,
    color,
  } = task;

  const getPriorityColor = (priority) => {
    switch (priority?.toLowerCase()) {
      case 'high':
        return 'text-red-500';
      case 'medium':
        return 'text-yellow-500';
      case 'low':
        return 'text-green-500';
      default:
        return 'text-gray-500';
    }
  };

  const getStatusIcon = (status) => {
    switch (status?.toLowerCase()) {
      case 'completed':
        return <CheckCircle2 className="w-4 h-4 text-green-500" />;
      default:
        return <Clock className="w-4 h-4 text-gray-400" />;
    }
  };

  return (
    <div
      className={`${color} ${
        isCompact ? 'w-80 mr-4 flex-shrink-0' : 'w-full'
      } rounded-2xl p-6 text-white shadow-lg hover:shadow-xl transition-all duration-300 cursor-pointer`}
    >
      <div className="flex items-start justify-between mb-4">
        <div className="flex-1">
          <h3 className="text-lg font-bold mb-2 line-clamp-2">{title}</h3>
          <div className="flex items-center space-x-2 text-sm opacity-90">
            {getStatusIcon(status)}
            <span className="capitalize">{status}</span>
            <span className={`px-2 py-1 rounded-full text-xs font-medium ${getPriorityColor(priority)} bg-white bg-opacity-20`}>
              {priority}
            </span>
          </div>
        </div>
      </div>

      {/* Day Label */}
      {dayLabel && (
        <div className="mb-4">
          <div className="flex items-center space-x-1 text-sm text-white text-opacity-90">
            <Tag className="w-4 h-4" />
            <span>{dayLabel}</span>
          </div>
        </div>
      )}

      {/* 하단 정보 */}
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-2">
            <div className="flex items-center space-x-1 text-sm opacity-90">
              <User className="w-4 h-4" />
              <span>evan</span>
            </div>
        </div>
        {dueDate && (
          <div className="text-sm opacity-90">
            {dueDate}
          </div>
        )}
      </div>
    </div>
  );
};

export default TaskCard;