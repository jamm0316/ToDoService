import { useState } from 'react';

const getCurrentDate = () => {
  const today = new Date();
  return today.toISOString().split('T')[0];
}

const useTaskForm = (initial = {}) => {
  const [formData, setFormData] = useState({
    projectId: null,
    colorId: 1,
    priority: 'MEDIUM', // Priority enum: LOW, MEDIUM, HIGH
    title: '',
    description: '',
    dayLabel: 'MORNING', // DayLabel enum: MORNING, AFTERNOON, EVENING
    schedule: {
      startDate: getCurrentDate(),
      startTime: '',
      startTimeEnabled: false,
      dueDate: '',
      dueTime: '',
      dueTimeEnabled: false,
    },
    status: 'PLANNING', // Status enum: PLANNING, IN_PROGRESS, COMPLETED, ON_HOLD
    ...initial,
  });

  const [errors, setErrors] = useState({});

  const setField = (field, value) => {
    // 중첩된 객체 필드 처리 (schedule.startTime 등)
    if (field.includes('.')) {
      const [parent, child] = field.split('.');
      setFormData(prev => ({
        ...prev,
        [parent]: {
          ...prev[parent],
          [child]: value,
        },
      }));
    } else {
      setFormData(prev => ({ ...prev, [field]: value }));
    }

    if (errors[field]) setErrors(prev => ({ ...prev, [field]: '' }));
  };

  const validate = () => {
    const e = {};

    if (!formData.projectId) e.projectId = '프로젝트 선택은 필수입니다.';
    if (!formData.colorId) e.colorId = '색상 선택은 필수입니다.';
    if (!formData.priority) e.priority = '우선순위는 필수입니다.';
    if (!formData.title?.trim()) e.title = '제목은 필수입니다.';
    if (!formData.status) e.status = '상태는 필수입니다.';

    // 스케줄 유효성 검사
    const { schedule } = formData;

    // startTimeEnabled가 true인데 startDate가 없는 경우
    if (schedule.startTimeEnabled && !schedule.startDate) {
      e['schedule.startDate'] = '시작 시간을 사용하려면 시작 날짜는 필수입니다.';
    }

    // dueTimeEnabled가 true인데 dueDate가 없는 경우
    if (schedule.dueTimeEnabled && !schedule.dueDate) {
      e['schedule.dueDate'] = '마감 시간을 사용하려면 마감 날짜는 필수입니다.';
    }

    // startTimeEnabled가 true인데 startTime이 없는 경우
    if (schedule.startTimeEnabled && !schedule.startTime) {
      e['schedule.startTime'] = '시작 시간이 활성화되면 시작 시간 값은 필수입니다.';
    }

    // dueTimeEnabled가 true인데 dueTime이 없는 경우
    if (schedule.dueTimeEnabled && !schedule.dueTime) {
      e['schedule.dueTime'] = '마감 시간이 활성화되면 마감 시간 값은 필수입니다.';
    }

    // 날짜 순서 검증
    if (schedule.startDate && schedule.dueDate && schedule.dueDate < schedule.startDate) {
      e['schedule.dueDate'] = '마감 날짜는 시작 날짜보다 뒤여야 합니다.';
    }

    // 같은 날짜에서 시간 순서 검증
    if (schedule.startDate && schedule.dueDate &&
      schedule.startDate === schedule.dueDate &&
      schedule.startTimeEnabled && schedule.dueTimeEnabled &&
      schedule.startTime && schedule.dueTime &&
      schedule.dueTime <= schedule.startTime) {
      e['schedule.dueTime'] = '같은 날짜에서 마감 시간은 시작 시간보다 뒤여야 합니다.';
    }

    setErrors(e);
    return Object.keys(e).length === 0;
  };

  // TaskCreateRequest 형태로 데이터 변환
  const getRequestData = () => {
    const requestData = {
      projectId: formData.projectId,
      colorId: formData.colorId,
      priority: formData.priority,
      title: formData.title.trim(),
      description: formData.description?.trim() || null, // 빈 문자열이면 null
      dayLabel: formData.dayLabel,
      schedule: null,
      status: formData.status,
    };

    // schedule이 모두 비어있지 않은 경우만 포함
    const { schedule } = formData;
    if (schedule.startDate || schedule.dueDate ||
      schedule.startTime || schedule.dueTime ||
      schedule.startTimeEnabled || schedule.dueTimeEnabled) {
      requestData.schedule = {
        startDate: schedule.startDate || null,
        startTime: schedule.startTime || null,
        startTimeEnabled: schedule.startTimeEnabled,
        dueDate: schedule.dueDate || null,
        dueTime: schedule.dueTime || null,
        dueTimeEnabled: schedule.dueTimeEnabled,
      };
    }

    return requestData;
  };

  // 폼 초기화
  const reset = () => {
    setFormData({
      projectId: null,
      colorId: 1,
      priority: 'MEDIUM',
      title: '',
      description: '',
      dayLabel: 'MORNING',
      schedule: {
        startDate: getCurrentDate(),
        startTime: '',
        startTimeEnabled: false,
        dueDate: '',
        dueTime: '',
        dueTimeEnabled: false,
      },
      status: 'PLANNING',
    });
    setErrors({});
  };

  return {
    formData,
    errors,
    setField,
    validate,
    getRequestData,
    reset
  };
};

export default useTaskForm;