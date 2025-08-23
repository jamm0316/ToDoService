import React from 'react';
import {Plus, Bell, Calendar, Home, Search} from 'lucide-react';

const BottomNav = ({ activeTab = 'home', onTabChange, onAddTask }) => {
  const navItems = [
    { id: 'home', icon: Home },
    { id: 'calendar', icon: Calendar },
    { id: 'add', icon: Plus, active: false, isCenter: true }, // 중앙 + 버튼
    { id: 'notifications', icon: Bell, hasNotification: true },
    { id: 'search', icon: Search }
  ];

  return (
    <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 px-6 py-4 backdrop-blur-sm bg-white/90">
      <div className="flex justify-around items-center max-w-md mx-auto relative">
        {navItems.map((item, index) => {
          const Icon = item.icon;

          // 중앙 + 버튼 특별 스타일링
          if (item.isCenter) {
            return (
              <button
                key={item.id}
                onClick={() => onAddTask?.()}
                className="relative -top-4 w-14 h-14 bg-gradient-to-r from-blue-600 to-purple-600 rounded-full flex items-center justify-center shadow-lg hover:shadow-xl transition-all hover:scale-105 active:scale-95"
              >
                <Icon className="w-7 h-7 text-white" />
                {/* 중앙 버튼 백그라운드 원 */}
                <div className="absolute inset-0 rounded-full bg-gradient-to-r from-blue-600 to-purple-600 opacity-20 scale-125 animate-pulse"></div>
              </button>
            );
          }
          const isActive = activeTab === item.id;


          // 일반 네비게이션 버튼들
          return (
            <button
              key={item.id}
              onClick={() => onTabChange(item.id)}
              className="relative p-3 rounded-xl hover:bg-gray-100 transition-colors"
            >
              <Icon className={`w-6 h-6 ${isActive ? 'text-blue-600' : 'text-gray-400'}`} />
              {item.hasNotification && (
                <div className="absolute top-2 right-2 w-3 h-3 bg-blue-600 rounded-full border-2 border-white"></div>
              )}
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default BottomNav;