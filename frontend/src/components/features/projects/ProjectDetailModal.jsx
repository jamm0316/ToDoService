import React, {useEffect, useMemo, useState} from 'react';
import {X, Edit2, Check, User, Calendar, Trash2, ChevronDown} from 'lucide-react';
import useDetailProject from '/src/hooks/project/useDetailProject.jsx';
import useUpdateProject from '/src/hooks/project/useUpdateProject.jsx';
import useUpdateFieldProject from '/src/hooks/project/useUpdateFieldProject.jsx';
import useDeleteProject from '/src/hooks/project/useDeleteProject.jsx';
import {colorMap, visibilityOptions, statusOptions} from '/src/data/project/constants.jsx';

import TextInput from '/src/components/ui/project/TextInput.jsx';
import DateRangePicker from '/src/components/ui/project/DateRangePicker.jsx';
import SelectField from '/src/components/ui/project/SelectField.jsx';

const ProjectDetailModal = ({projectId, isOpen, onClose, onUpdate}) => {
  const {data: project, loading, error, refetch} = useDetailProject(projectId);
  const {updateProject, loading: updateLoading} = useUpdateProject();
  const {updateFieldProject, loading: updateFieldLoading} = useUpdateFieldProject();
  const {deleteProject, loading: deleteLoading} = useDeleteProject();

  const detail = useMemo(() => project ?? null, [project]);

  // 인라인 편집 상태
  const [editTitle, setEditTitle] = useState(false);
  const [editDesc, setEditDesc] = useState(false);
  const [editVisibility, setEditVisibility] = useState(false);
  const [editStatus, setEditStatus] = useState(false);
  const [editPeriod, setEditPeriod] = useState(false);

  // 편집 값
  const [form, setForm] = useState({
    name: '',
    description: '',
    visibility: '',
    status: '',
    startDate: '',
    endDate: '',
    colorId: 1,
    progress: 0,
  });

  // detail 변경 시 폼 초기화
  useEffect(() => {
    if (!detail) return;
    setForm({
      name: detail.name ?? '',
      description: detail.description ?? '',
      visibility: detail.visibility ?? 'PUBLIC',
      status: detail.status ?? 'IN_PROGRESS',
      startDate: detail.period?.startDate ?? '',
      endDate: detail.period?.endDate ?? '',
      colorId: Number(detail.colorId ?? 1),
      progress: typeof detail.progress === 'number' ? Math.round(detail.progress) : 0,
    });
  }, [detail]);

  // 모달 열릴 때 배경 스크롤 잠금
  useEffect(() => {
    if (isOpen) {
      const prev = document.body.style.overflow;
      document.body.style.overflow = 'hidden';
      return () => {
        document.body.style.overflow = prev;
      };
    }
  }, [isOpen]);

  // 모달 닫힐 때 인라인 편집 상태 리셋
  const resetInlineEdits = () => {
    setEditTitle(false);
    setEditDesc(false);
    setEditVisibility(false);
    setEditStatus(false);
    setEditPeriod(false);
  };

  useEffect(() => {
    if (!isOpen) {
      resetInlineEdits();
    }
  }, [isOpen]);

  if (!isOpen) return null;

  const headerColor = colorMap[detail?.colorId] ?? 'bg-gradient-to-br from-slate-500 to-slate-700';
  const periodText = `${form.startDate || ''} ~ ${form.endDate || ''}`;

  const saveField = async (fieldType, value) => {
    try {
      const res = await updateFieldProject(projectId, {
        fieldType,
        value,
      });
      if (res?.success) {
        await refetch();
        onUpdate && onUpdate();
        return true;
      }
      return false;
    } catch (err) {
      console.error(`Failed to update field ${fieldType}:`, err);
      return false;
    }
  };

  // 전체 저장
  const handleSaveAll = async () => {
    const payload = {
      name: form.name,
      description: form.description || null,
      visibility: form.visibility,
      status: form.status,
      period: {startDate: form.startDate, endDate: form.endDate},
      progress: form.progress,
      colorId: form.colorId,
    };
    const res = await updateProject(projectId, payload);
    if (res?.success) {
      resetInlineEdits();
      await refetch();
      onUpdate && onUpdate();
    }
  };

  const handleBackdropClick = (e) => {
    if (e.target === e.currentTarget) {
      resetInlineEdits();
      onClose();
    }
  };

  const handleCloseClick = () => {
    resetInlineEdits();
    onClose();
  };

  const handleDelete = async () => {
    if (window.confirm('정말로 이 프로젝트를 삭제하시겠습니까?')) {
      const res = await deleteProject(projectId);
      if (res?.success) {
        resetInlineEdits();
        onClose();
        onUpdate && onUpdate();
      }
    }
  };

  // Custom Dropdown Component for better visual consistency
  const CustomDropdown = ({value, options, onChange, placeholder, className = ""}) => {
    const [isOpen, setIsOpen] = useState(false);
    const selectedOption = options.find(opt => opt.value === value);

    return (
      <div className={`relative ${className}`}>
        <button
          className="w-full bg-white/20 backdrop-blur-sm border border-white/30 rounded-lg px-3 py-2 text-left text-white text-sm font-medium flex items-center justify-between hover:bg-white/30 transition-all"
          onClick={() => setIsOpen(!isOpen)}
        >
          <span>{selectedOption?.label || placeholder}</span>
          <ChevronDown className={`w-4 h-4 transition-transform ${isOpen ? 'rotate-180' : ''}`}/>
        </button>

        {isOpen && (
          <div
            className="absolute top-full left-0 right-0 mt-1 bg-white rounded-lg shadow-lg border border-gray-200 z-10 max-h-40 overflow-y-auto">
            {options.map((option) => (
              <button
                key={option.value}
                className="w-full px-3 py-2 text-left text-gray-700 hover:bg-gray-50 first:rounded-t-lg last:rounded-b-lg transition-colors"
                onClick={() => {
                  onChange(option.value);
                  setIsOpen(false);
                }}
              >
                {option.label}
              </button>
            ))}
          </div>
        )}
      </div>
    );
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div className="bg-white rounded-2xl p-6 max-w-md w-full mx-4">
          <div className="flex items-center justify-center py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"/>
            <span className="ml-2 text-gray-600">프로젝트 정보를 불러오는 중...</span>
          </div>
        </div>
      </div>
    );
  }

  if (error || !detail) {
    return (
      <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div className="bg-white rounded-2xl p-6 max-w-md w-full mx-4">
          <div className="text-center py-8">
            <p className="text-red-600 mb-4">프로젝트 정보를 불러올 수 없습니다.</p>
            <button onClick={handleCloseClick}
                    className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600">
              닫기
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50" onClick={handleBackdropClick}>
      <div className="bg-white rounded-2xl max-w-md w-full mx-4 max-h-[90vh] overflow-y-auto">
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h2 className="text-xl font-bold text-gray-800">프로젝트 상세</h2>
          <button onClick={handleCloseClick}
                  className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100">
            <X className="w-5 h-5 text-gray-500"/>
          </button>
        </div>

        {/* Content */}
        <div className="p-6 space-y-6">
          {/* Project Header */}
          <div className={`${headerColor} rounded-2xl p-6 text-white relative`}>
            <div className="flex items-center justify-between mb-3">
              <div className="w-12 h-12 bg-white/20 rounded-full flex items-center justify-center">
                <User className="w-6 h-6"/>
              </div>

              {/* Visibility 인라인 편집 */}
              <div className="flex items-center space-x-2">
                {!editVisibility ? (
                  <>
                    <span className="text-sm opacity-90 font-medium">
                      {visibilityOptions.find(opt => opt.value === form.visibility)?.label || form.visibility}
                    </span>
                    <button
                      className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                      onClick={() => setEditVisibility(true)}
                      title="Edit visibility"
                    >
                      <Edit2 className="w-4 h-4"/>
                    </button>
                  </>
                ) : (
                  <>
                    <CustomDropdown
                      value={form.visibility}
                      options={visibilityOptions}
                      onChange={(v) => setForm((s) => ({...s, visibility: v}))}
                      placeholder="Visibility"
                      className="min-w-[120px]"
                    />
                    <button
                      className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                      disabled={updateFieldLoading}
                      onClick={async () => {
                        if (!form.visibility) {
                          console.error('Visibility is undefined or empty');
                          return;
                        }
                        const ok = await saveField('visibility', form.visibility);
                        if (ok) setEditVisibility(false);
                      }}
                      title="Save visibility"
                    >
                      <Check className="w-4 h-4"/>
                    </button>
                  </>
                )}
              </div>
            </div>

            {/* Title 인라인 편집 */}
            <div className="flex items-start justify-between mb-4">
              {!editTitle ? (
                <>
                  <h3 className="text-xl font-bold break-words">{form.name}</h3>
                  <button
                    className="ml-3 w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                    onClick={() => setEditTitle(true)}
                    title="Edit title"
                  >
                    <Edit2 className="w-4 h-4"/>
                  </button>
                </>
              ) : (
                <div className="flex-1 flex items-center space-x-2">
                  <input
                    value={form.name}
                    onChange={(e) => setForm((s) => ({...s, name: e.target.value}))}
                    placeholder="Project title"
                    className="w-full bg-transparent border-none outline-none text-xl font-bold text-white placeholder-white/60"
                  />
                  <button
                    className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                    disabled={updateFieldLoading}
                    onClick={async () => {
                      const ok = await saveField('name', form.name);
                      if (ok) setEditTitle(false);
                    }}
                    title="Save title"
                  >
                    <Check className="w-4 h-4"/>
                  </button>
                </div>
              )}
            </div>

            {/* Status 인라인 편집 */}
            <div className="flex items-center justify-between mb-3">
              <span className="text-sm opacity-90">상태</span>
              <div className="flex items-center space-x-2">
                {!editStatus ? (
                  <>
                    <span
                      className="text-sm font-medium">{statusOptions.find(opt => opt.value === form.status)?.label || form.status}</span>
                    <button
                      className="w-6 h-6 flex items-center justify-center rounded-full hover:bg-white/10"
                      onClick={() => setEditStatus(true)}
                      title="Edit status"
                    >
                      <Edit2 className="w-3 h-3"/>
                    </button>
                  </>
                ) : (
                  <>
                    <CustomDropdown
                      value={form.status}
                      options={statusOptions}
                      onChange={(v) => setForm((s) => ({...s, status: v}))}
                      placeholder="상태 선택"
                      className="min-w-[100px]"
                    />
                    <button
                      className="w-6 h-6 flex items-center justify-center rounded-full hover:bg-white/10"
                      disabled={updateFieldLoading}
                      onClick={async () => {
                        if (!form.status) {
                          console.error('Status is undefined or empty');
                          return;
                        }
                        const ok = await saveField('status', form.status);
                        if (ok) setEditStatus(false);
                      }}
                      title="Save status"
                    >
                      <Check className="w-3 h-3"/>
                    </button>
                  </>
                )}
              </div>
            </div>

            {/* Progress 표시 */}
            <div className="flex items-center justify-between">
              <span className="text-sm opacity-90">Progress</span>
              <span className="text-sm font-medium">{Math.round(detail.progress * 100)}%</span>
            </div>
            <div className="w-full bg-white/20 rounded-full h-2 mt-2">
              <div
                className="bg-white rounded-full h-2 transition-all duration-300"
                style={{width: `${Math.round(detail.progress * 100)}%`}}
              />
            </div>
          </div>

          {/* Description 인라인 편집 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">설명</label>
            {!editDesc ? (
              <div className="flex items-start justify-between">
                <p className="text-gray-600 whitespace-pre-wrap break-words flex-1">
                  {form.description || '설명이 없습니다.'}
                </p>
                <button
                  className="ml-2 w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100"
                  onClick={() => setEditDesc(true)}
                  title="Edit description"
                >
                  <Edit2 className="w-4 h-4 text-gray-600"/>
                </button>
              </div>
            ) : (
              <div className="flex items-start space-x-2">
                <textarea
                  className="w-full bg-transparent border-none outline-none text-gray-600 px-0 py-0 resize-none"
                  rows={3}
                  value={form.description}
                  onChange={(e) => setForm((s) => ({...s, description: e.target.value}))}
                  placeholder="프로젝트 설명"
                />
                <button
                  className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-gray-100"
                  disabled={updateFieldLoading}
                  onClick={async () => {
                    const ok = await saveField('description', form.description || null);
                    if (ok) setEditDesc(false);
                  }}
                  title="Save description"
                >
                  <Check className="w-5 h-5 text-gray-700"/>
                </button>
              </div>
            )}
          </div>

          {/* Period 인라인 편집 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              <Calendar className="w-4 h-4 inline mr-2"/>
              기간
            </label>

            {!editPeriod ? (
              <div className="flex items-center justify-between">
                <p className="text-gray-600">{periodText}</p>
                <button
                  className="ml-2 w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100"
                  onClick={() => setEditPeriod(true)}
                  title="Edit period"
                >
                  <Edit2 className="w-4 h-4 text-gray-600"/>
                </button>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <DateRangePicker
                  start={form.startDate}
                  end={form.endDate}
                  onChangeStart={(v) => setForm((s) => ({...s, startDate: v}))}
                  onChangeEnd={(v) => setForm((s) => ({...s, endDate: v}))}
                />
                <button
                  className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-gray-100"
                  disabled={updateFieldLoading}
                  onClick={async () => {
                    const ok = await saveField('period', {
                      startDate: form.startDate,
                      endDate: form.endDate,
                      actualEndDate: null
                    });
                    if (ok) setEditPeriod(false);
                  }}
                  title="Save period"
                >
                  <Check className="w-5 h-5 text-gray-700"/>
                </button>
              </div>
            )}
          </div>
        </div>

        {/* Footer */}
        <div className="p-6 border-t border-gray-200">
          <div className="flex space-x-3">
            <button
              onClick={() => {
                resetInlineEdits();
                onClose();
              }}
              disabled={updateLoading}
              className="flex-1 flex items-center justify-center py-3 bg-green-600 text-white rounded-xl font-medium hover:bg-green-700 disabled:bg-gray-400 transition-colors"
            >
              <Check className="w-4 h-4 mr-2"/>
              {updateLoading ? '저장 중...' : '완료'}
            </button>

            <button
              onClick={handleDelete}
              disabled={deleteLoading}
              className="flex-1 flex items-center justify-center py-3 bg-red-600 text-white rounded-xl font-medium hover:bg-red-700 disabled:bg-gray-400 transition-colors"
            >
              <Trash2 className="w-4 h-4 mr-2"/>
              {deleteLoading ? '삭제 중...' : '삭제'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProjectDetailModal;