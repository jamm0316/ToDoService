const TextInput = ({ label, value, onChange, placeholder, error }) => {
  return (
    <div className="mb-8">
      <label className="block text-sm text-gray-500 mb-3 uppercase tracking-wide">{label}</label>
      <input
        type="text"
        value={value}
        onChange={e => onChange(e.target.value)}
        placeholder={placeholder}
        className={`w-full text-2xl font-semibold border-none outline-none bg-transparent placeholder-gray-400 ${
          error ? 'text-red-500' : 'text-gray-800'
        }`}
      />
      {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
    </div>
  );
};

export default TextInput;