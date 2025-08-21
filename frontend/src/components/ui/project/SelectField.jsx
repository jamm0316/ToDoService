import { ChevronDown } from 'lucide-react';

const SelectField = ({ label, value, onChange, options }) => {
  return (
    <div className="mb-8">
      <label className="block text-sm text-gray-500 mb-3 uppercase tracking-wide">{label}</label>
      <div className="relative">
        <select
          value={value}
          onChange={e => onChange(e.target.value)}
          className="w-full text-lg font-semibold border-none outline-none bg-transparent appearance-none cursor-pointer text-gray-800 pr-8"
        >
          {options.map(o => <option key={o.value} value={o.value}>{o.label}</option>)}
        </select>
        <ChevronDown className="absolute right-0 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400 pointer-events-none" />
      </div>
    </div>
  );
};

export default SelectField;