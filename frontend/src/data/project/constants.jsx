export const projectColors = [
  { id: 1, color: '#8B5CF6', name: 'PURPLE' },
  { id: 2, color: '#3B82F6', name: 'BLUE' },
  { id: 3, color: '#EF4444', name: 'RED' },
  { id: 4, color: '#10B981', name: 'GREEN' },
  { id: 5, color: '#F59E0B', name: 'ORANGE' },
  { id: 6, color: '#EC4899', name: 'PINK' },
];

export const colorMap = {
  1: 'bg-gradient-to-br from-blue-600 to-purple-700',
  2: 'bg-gradient-to-br from-blue-500 to-cyan-500',
  3: 'bg-gradient-to-br from-purple-600 to-pink-600',
  4: 'bg-gradient-to-br from-green-500 to-teal-600',
  5: 'bg-gradient-to-br from-orange-500 to-amber-600',
  6: 'bg-gradient-to-br from-pink-500 to-rose-600',
};

export const statusOptions = [
  { value: 'PLANNING', label: '진행예정' },
  { value: 'IN_PROGRESS', label: '진행중' },
  { value: 'COMPLETED', label: '완료' },
  { value: 'ON_HOLD', label: '보류' },
];

export const visibilityOptions = [
  { value: 'PUBLIC', label: '전체공개', description: '모든 사용자가 볼 수 있습니다' },
  { value: 'PRIVATE', label: '비공개', description: '나만 볼 수 있습니다' },
  { value: 'TEAM', label: '팀공개', description: '팀 멤버만 볼 수 있습니다' },
];