export const taskStatusOptions = [
  { value: 'PLANNING', label: '진행예정' },
  { value: 'IN_PROGRESS', label: '진행중' },
  { value: 'COMPLETED', label: '완료' },
  { value: 'ON_HOLD', label: '보류' },
];

export const taskPriorityOptions = [
  { value: 'LOW', label: '낮음', color: '#10B981', bgColor: 'bg-green-100', textColor: 'text-green-800' },
  { value: 'MEDIUM', label: '보통', color: '#F59E0B', bgColor: 'bg-yellow-100', textColor: 'text-yellow-800' },
  { value: 'HIGH', label: '높음', color: '#EF4444', bgColor: 'bg-red-100', textColor: 'text-red-800' },
];

export const dayLabelOptions = [
  { value: 'MORNING', label: '아침', short: '🌱 아침' },
  { value: 'AFTERNOON', label: '점심', short: '☀️ 점심' },
  { value: 'EVENING', label: '저녁', short: '🌙 저녁' },
];