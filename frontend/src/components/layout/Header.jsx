import React from 'react';
import {Menu, User} from 'lucide-react';

const Header = () => {
  return (
    <header className="px-6 pt-12 pb-6 bg-white">
      <div className="flex items-center justify-between mb-6">
        <Menu className="w-6 h-6 text-gray-800 cursor-pointer hover:text-gray-600" />
        <div className="w-10 h-10 bg-gradient-to-r from-pink-500 to-purple-500 rounded-full flex items-center justify-center cursor-pointer hover:shadow-lg transition-shadow">
          <User className="w-6 h-6 text-white" />
        </div>
      </div>

      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-800 mb-1">Hello, John!</h1>
        <p className="text-gray-500">Have a nice day!</p>
      </div>
    </header>
  );
};

export default Header;