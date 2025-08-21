import React, {useState} from 'react';
import { Search } from 'lucide-react';

const SearchBar = ({placeholder, onSearch}) => {
  const [query, setQuery] = useState('');

  return (
    <div className="relative mb-6">
      <input
        type="text"
        placeholder={placeholder}
        value={query}
        onChange={(e) => {
          setQuery(e.target.value);
          onSearch?.(e.target.value);
        }}
        className="w-full pl-4 pr-12 py-3 bg-gray-100 rounded-xl border-none outline-none text-gray-700 placeholder-gray-400 focus:bg-white focus:shadow-sm transition-all"
      />
      <Search className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
      <div className="absolute right-12 top-1/2 transform -translate-y-1/2">
        <div className="w-6 h-6 bg-blue-600 rounded-lg flex items-center justify-center">
          <div className="w-3 h-3 bg-white rounded-sm"></div>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;