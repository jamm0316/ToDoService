// ProjectDetailModal.jsx
import React, { useEffect, useMemo, useState } from 'react';
import { X, Edit2, Check, User, Calendar, Trash2 } from 'lucide-react';
import useDetailProject from '/src/hooks/project/useDetailProject.jsx';
import useUpdateProject from '/src/hooks/project/useUpdateProject.jsx';
import useDeleteProject from '/src/hooks/project/useDeleteProject.jsx';
import { colorMap, visibilityOptions } from '/src/data/project/constants.jsx';

import TextInput from '/src/components/ui/project/TextInput.jsx';
import DateRangePicker from '/src/components/ui/project/DateRangePicker.jsx';
import SelectField from '/src/components/ui/project/SelectField.jsx';

const ProjectDetailModal = ({ projectId, isOpen, onClose, onUpdate }) => {
  const { data: project, loading, error, refetch } = useDetailProject(projectId);
  const { updateProject, loading: updateLoading } = useUpdateProject();
  const { deleteProject, loading: deleteLoading } = useDeleteProject();

  const detail = useMemo(() => project ?? null, [project]);

  // 인라인 편집 상태
  const [editTitle, setEditTitle] = useState(false);
  const [editDesc, setEditDesc] = useState(false);
  const [editVisibility, setEditVisibility] = useState(false);
  const [editPeriod, setEditPeriod] = useState(false);

  // 편집 값
  const [form, setForm] = useState({
    name: '',
    description: '',
    visibility: '',
    isPublic: true,
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
      isPublic: detail.visibility === 'PUBLIC',
      startDate: detail.period?.startDate ?? '',
      endDate: detail.period?.endDate ?? '',
      colorId: Number(detail.colorId ?? 1),
      progress: typeof detail.progress === 'number' ? Math.round(detail.progress) : 0,
    });
  }, [detail]);

  // ② 모달 열릴 때 배경 스크롤 잠금
  useEffect(() => {
    if (isOpen) {
      const prev = document.body.style.overflow;
      document.body.style.overflow = 'hidden';
      return () => { document.body.style.overflow = prev; };
    }
  }, [isOpen]);

  // ③ 모달 닫힐 때 인라인 편집 상태 리셋
  const resetInlineEdits = () => {
    setEditTitle(false);
    setEditDesc(false);
    setEditVisibility(false);
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

  // 공통 업데이트 헬퍼(부분 업데이트)
  const savePartial = async (partial) => {
    const payload = {
      ...(partial.name != null && { name: partial.name }),
      ...(partial.description != null && { description: partial.description }),
      ...(partial.visibility != null && {
        visibility: partial.visibility,
        isPublic: partial.isPublic ?? partial.visibility === 'PUBLIC',
      }),
      ...(partial.startDate != null || partial.endDate != null
        ? { period: { startDate: partial.startDate ?? form.startDate, endDate: partial.endDate ?? form.endDate } }
        : {}),
      ...(partial.progress != null && { progress: partial.progress }),
      ...(partial.colorId != null && { colorId: partial.colorId }),
    };
    const res = await updateProject(projectId, payload);
    if (res?.success) {
      await refetch();
      onUpdate && onUpdate();
      return true;
    }
    return false;
  };

  // 전체 저장
  const handleSaveAll = async () => {
    const payload = {
      name: form.name,
      description: form.description || null,
      visibility: form.visibility,
      isPublic: form.visibility === 'PUBLIC',
      period: { startDate: form.startDate, endDate: form.endDate },
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
      resetInlineEdits();       // 닫기 전에 리셋
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

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div className="bg-white rounded-2xl p-6 max-w-md w-full mx-4">
          <div className="flex items-center justify-center py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600" />
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
            <button onClick={handleCloseClick} className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600">
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
          <button onClick={handleCloseClick} className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100">
            <X className="w-5 h-5 text-gray-500" />
          </button>
        </div>

        {/* Content */}
        <div className="p-6 space-y-6">
          {/* Project Header */}
          <div className={`${headerColor} rounded-2xl p-6 text-white`}>
            <div className="flex items-center justify-between mb-3">
              <div className="w-12 h-12 bg-white/20 rounded-full flex items-center justify-center">
                <User className="w-6 h-6" />
              </div>

              {/* Visibility 인라인 편집 */}
              <div className="flex items-center space-x-2">
                {!editVisibility ? (
                  <>
                    <span className="text-sm opacity-90 font-medium">{form.visibility}</span>
                    <button
                      className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                      onClick={() => setEditVisibility(true)}
                      title="Edit visibility"
                    >
                      <Edit2 className="w-4 h-4" />
                    </button>
                  </>
                ) : (
                  <>
                    {/* 편집 시에도 시각 톤 유지: 투명 배경 */}
                    <div className="rounded-lg">
                      <SelectField
                        label=""
                        hideLabel
                        value={form.visibility}
                        onChange={(v) => setForm((s) => ({ ...s, visibility: v, isPublic: v === 'PUBLIC' }))}
                        options={visibilityOptions}
                      />
                    </div>
                    <button
                      className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                      disabled={updateLoading}
                      onClick={async () => {
                        const ok = await savePartial({ visibility: form.visibility, isPublic: form.visibility === 'PUBLIC' });
                        if (ok) setEditVisibility(false);
                      }}
                      title="Save visibility"
                    >
                      <Check className="w-4 h-4" />
                    </button>
                  </>
                )}
              </div>
            </div>

            {/* Title 인라인 편집 */}
            <div className="flex items-start justify-between">
              {!editTitle ? (
                <>
                  <h3 className="text-xl font-bold mb-2 break-words">{form.name}</h3>
                  <button
                    className="ml-3 w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                    onClick={() => setEditTitle(true)}
                    title="Edit title"
                  >
                    <Edit2 className="w-4 h-4" />
                  </button>
                </>
              ) : (
                <div className="flex-1 flex items-center space-x-2">
                  {/* 기존 서식과 동일한 톤으로 입력 */}
                  <input
                    value={form.name}
                    onChange={(e) => setForm((s) => ({ ...s, name: e.target.value }))}
                    placeholder="Project title"
                    className="w-full bg-transparent border-none outline-none text-xl font-bold text-white placeholder-white/60"
                  />
                  <button
                    className="w-8 h-8 flex items-center justify-center rounded-full hover:bg-white/10"
                    disabled={updateLoading}
                    onClick={async () => {
                      const ok = await savePartial({ name: form.name });
                      if (ok) setEditTitle(false);
                    }}
                    title="Save title"
                  >
                    <Check className="w-4 h-4" />
                  </button>
                </div>
              )}
            </div>

            {/* Progress 표시 */}
            <div className="flex items-center justify-between mt-3">
              <span className="text-sm opacity-90">Progress</span>
              <span className="text-sm font-medium">{detail.progress * 100}%</span>
            </div>
            <div className="w-full bg-white/20 rounded-full h-2 mt-2">
              <div className="bg-white rounded-full h-2 transition-all duration-300" style={{ width: `${detail.progress * 100}%` }} />
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
                  <Edit2 className="w-4 h-4 text-gray-600" />
                </button>
              </div>
            ) : (
              <div className="flex items-start space-x-2">
                {/* 기존 서식과 동일 톤 */}
                <textarea
                  className="w-full bg-transparent border-none outline-none text-gray-600 px-0 py-0 resize-none"
                  rows={3}
                  value={form.description}
                  onChange={(e) => setForm((s) => ({ ...s, description: e.target.value }))}
                  placeholder="프로젝트 설명"
                />
                <button
                  className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-gray-100"
                  disabled={updateLoading}
                  onClick={async () => {
                    const ok = await savePartial({ description: form.description || null });
                    if (ok) setEditDesc(false);
                  }}
                  title="Save description"
                >
                  <Check className="w-5 h-5 text-gray-700" />
                </button>
              </div>
            )}
          </div>

          {/* Period 인라인 편집 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              <Calendar className="w-4 h-4 inline mr-2" />
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
                  <Edit2 className="w-4 h-4 text-gray-600" />
                </button>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                {/* DateRangePicker 자체 스타일은 유지, 주변 톤은 심플하게 */}
                <DateRangePicker
                  start={form.startDate}
                  end={form.endDate}
                  onChangeStart={(v) => setForm((s) => ({ ...s, startDate: v }))}
                  onChangeEnd={(v) => setForm((s) => ({ ...s, endDate: v }))}
                />
                <button
                  className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-gray-100"
                  disabled={updateLoading}
                  onClick={async () => {
                    const ok = await savePartial({ startDate: form.startDate, endDate: form.endDate });
                    if (ok) setEditPeriod(false);
                  }}
                  title="Save period"
                >
                  <Check className="w-5 h-5 text-gray-700" />
                </button>
              </div>
            )}
          </div>
        </div>

        {/* Footer */}
        <div className="p-6 border-t border-gray-200">
          <div className="flex space-x-3">
            {/* 완료 버튼: 기존 스타일 그대로, 색만 초록 + 아이콘만 체크 */}
            <button
              onClick={handleSaveAll}
              disabled={updateLoading}
              className="flex-1 flex items-center justify-center py-3 bg-green-600 text-white rounded-xl font-medium hover:bg-green-700 disabled:bg-gray-400 transition-colors"
            >
              <Check className="w-4 h-4 mr-2" />
              {updateLoading ? '저장 중...' : '완료'}
            </button>

            {/* 삭제 버튼 */}
            <button
              onClick={handleDelete}
              disabled={deleteLoading}
              className="flex-1 flex items-center justify-center py-3 bg-red-600 text-white rounded-xl font-medium hover:bg-red-700 disabled:bg-gray-400 transition-colors"
            >
              <Trash2 className="w-4 h-4 mr-2" />
              {deleteLoading ? '삭제 중...' : '삭제'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProjectDetailModal;