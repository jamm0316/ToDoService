import { useState } from 'react';

const useProjectForm = (initial = {}) => {
  const [formData, setFormData] = useState({
    colorId: 1,
    name: '',
    status: 'PLANNING',
    startDate: '',
    endDate: '',
    description: '',
    isPublic: true,
    visibility: 'PUBLIC',
    selectedTags: [],
    ...initial,
  });

  const [errors, setErrors] = useState({});

  const setField = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) setErrors(prev => ({ ...prev, [field]: '' }));
  };

  const toggleTag = (tag) => {
    setFormData(prev => ({
      ...prev,
      selectedTags: prev.selectedTags.includes(tag)
        ? prev.selectedTags.filter(t => t !== tag)
        : [...prev.selectedTags, tag],
    }));
  };

  const validate = () => {
    const e = {};
    if (!formData.name.trim()) e.name = '프로젝트 이름은 필수입니다.';
    if (!formData.startDate) e.startDate = '시작일은 필수입니다.';
    if (!formData.endDate) e.endDate = '종료일은 필수입니다.';
    if (formData.startDate && formData.endDate && formData.startDate > formData.endDate) {
      e.endDate = '종료일은 시작일보다 뒤여야 합니다.';
    }
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  return { formData, errors, setField, toggleTag, validate };
};

export default useProjectForm;