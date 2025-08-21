import { ArrowLeft } from 'lucide-react';

const PageHeader = ({ onBack }) => {
  return (
    <header className="flex items-center justify-between p-6 border-b border-gray-100">
      <button onClick={onBack} className="p-2 hover:bg-gray-100 rounded-full transition-colors" aria-label="Back">
        <ArrowLeft
          className="w-6 h-6 text-gray-800"/>
      </button>
    </header>
  );
};

export default PageHeader;