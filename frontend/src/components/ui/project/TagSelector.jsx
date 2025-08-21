const TagSelector = ({ tags, selected, onToggle }) => {
  return (
    <div className="flex flex-wrap gap-3">
      {tags.map(tag => {
        const on = selected.includes(tag);
        return (
          <button
            key={tag}
            onClick={() => onToggle(tag)}
            className={`px-4 py-3 rounded-2xl text-sm font-medium transition-all ${
              on ? 'bg-pink-100 text-pink-700 border-2 border-pink-200' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
          >
            {tag}
          </button>
        );
      })}
    </div>
  );
};

export default TagSelector;