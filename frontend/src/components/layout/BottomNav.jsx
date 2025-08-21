import React from 'react';
import {Bell, Calendar, Home, Search} from 'lucide-react';

const BottomNav = ({ activeTab, onTabChange }) => {
  const navItems = [
    { id: 'home', icon: Home, active: true },
    { id: 'calendar', icon: Calendar, active: false },
    { id: 'notifications', icon: Bell, active: false, hasNotification: true },
    { id: 'search', icon: Search, active: false }
  ];

  return (
    <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 px-6 py-4 backdrop-blur-sm bg-white/90">
      <div className="flex justify-around items-center max-w-md mx-auto">
        {navItems.map((item) => {
          const Icon = item.icon;
          return (
            <button
              key={item.id}
              onClick={() => onTabChange(item.id)}
              className="relative p-3 rounded-xl hover:bg-gray-100 transition-colors"
            >
              <Icon className={`w-6 h-6 ${item.active ? 'text-blue-600' : 'text-gray-400'}`} />
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