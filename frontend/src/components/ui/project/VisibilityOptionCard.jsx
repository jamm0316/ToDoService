const VisibilityOptionCard = ({ active, label, description, onClick }) => {
  return (
    <button
      onClick={onClick}
      className={`w-full text-left p-4 rounded-2xl transition-all ${
        active ? 'bg-blue-50 border-2 border-blue-200' : 'bg-gray-50 hover:bg-gray-100'
      }`}
    >
      <div className="font-semibold text-gray-900 text-lg">{label}</div>
      <div className="text-sm text-gray-600 mt-1">{description}</div>
    </button>
  );
};

export default VisibilityOptionCard;