import React from 'react';

const TabSelector = ({ activeTab, onTabChange }) => {
  const tabs = [
    { id: 'today', label: '오늘 할일', isActive: true },
    { id: 'projects', label: '프로젝트', isActive: false },
    { id: 'task', label: '할일들', isActive: false }
  ];

  return (
    <div className="flex space-x-1 bg-gray-100 rounded-xl p-1 mb-6">
      {tabs.map((tab) => (
        <button
          key={tab.id}
          onClick={() => onTabChange(tab.id)}
          className={`flex-1 py-3 px-4 rounded-lg text-sm font-medium transition-all ${
            activeTab === tab.id
              ? 'bg-white text-gray-800 shadow-sm'
              : 'text-gray-500 hover:text-gray-700'
          }`}
        >
          <div className="flex items-center justify-center space-x-2">
            <span>{tab.label}</span>
            {tab.isActive && (
              <span className="w-2 h-2 bg-red-500 rounded-full"></span>
            )}
          </div>
        </button>
      ))}
    </div>
  );
};

export default TabSelector;