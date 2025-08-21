const ColorPicker = ({ colors, value, onChange }) => {
  const selected = colors.find(c => c.id === value);

  return (
    <>
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-4xl font-bold text-gray-900">New Project</h1>
        <div
          className="w-14 h-14 rounded-full cursor-pointer hover:scale-110 transition-transform shadow-lg"
          style={{ backgroundColor: selected?.color }}
          aria-label="Selected color"
        />
      </div>

      <div className="flex space-x-4 mb-8 justify-center">
        {colors.map(c => (
          <button
            key={c.id}
            onClick={() => onChange(c.id)}
            className={`w-10 h-10 rounded-full transition-all ${
              value === c.id ? 'scale-125 ring-4 ring-offset-2 ring-gray-300' : 'hover:scale-110'
            }`}
            style={{ backgroundColor: c.color }}
            aria-label={c.name}
          />
        ))}
      </div>
    </>
  );
};

export default ColorPicker;