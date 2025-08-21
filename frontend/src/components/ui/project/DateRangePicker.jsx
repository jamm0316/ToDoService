const DateRangePicker = ({ start, end, onChangeStart, onChangeEnd, errors = {} }) => {
  return (
    <div className="mb-8">
      <div className="grid grid-cols-2 gap-6">
        <div>
          <label className="block text-sm text-gray-500 mb-2 uppercase tracking-wide">Start date</label>
          <input
            type="date"
            value={start}
            onChange={e => onChangeStart(e.target.value)}
            className={`w-full text-lg font-semibold border-none outline-none bg-transparent ${
              errors.startDate ? 'text-red-500' : 'text-gray-800'
            }`}
          />
          {errors.startDate && <p className="text-red-500 text-xs mt-1">{errors.startDate}</p>}
        </div>
        <div>
          <label className="block text-sm text-gray-500 mb-2 uppercase tracking-wide">End date</label>
          <input
            type="date"
            value={end}
            onChange={e => onChangeEnd(e.target.value)}
            className={`w-full text-lg font-semibold border-none outline-none bg-transparent ${
              errors.endDate ? 'text-red-500' : 'text-gray-800'
            }`}
          />
          {errors.endDate && <p className="text-red-500 text-xs mt-1">{errors.endDate}</p>}
        </div>
      </div>
    </div>
  );
};

export default DateRangePicker;