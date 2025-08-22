import React, { useState } from 'react';
import { Calendar, Clock, Flag, Folder, Palette, FileText, Tag } from 'lucide-react';
import { taskColors, taskStatusOptions, taskPriorityOptions, dayLabelOptions } from '/src/data/task/constants.jsx';

const TaskForm = ({ form, onSubmit, loading = false }) => {
  const { formData, errors, setField, validate, getRequestData } = form;
  const [showColorPicker, setShowColorPicker] = useState(false);

  const handleSubmit = () => {
    if (validate()) {
      onSubmit(getRequestData());
    }
  };

  const handleTimeEnabledChange = (type, enabled) => {
    setField(`schedule.${type}TimeEnabled`, enabled);
    if (!enabled) {
      setField(`schedule.${type}Time`, '');
    }
  };

  // StickyFooter 컴포넌트
  const StickyFooter = ({ children }) => {
    return (
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 p-6">
        <div className="max-w-md mx-auto">{children}</div>
      </div>
    );
  };

  // 토글 스위치 컴포넌트
  const ToggleSwitch = ({ checked, onChange, label }) => {
    return (
      <label className="flex items-center cursor-pointer group">
        <div className="relative">
          <input
            type="checkbox"
            checked={checked}
            onChange={onChange}
            className="sr-only"
          />
          <div className={`block w-14 h-8 rounded-full transition-all duration-300 ease-in-out transform group-hover:scale-105 ${
            checked ? 'bg-blue-600 shadow-lg' : 'bg-gray-300'
          }`}></div>
          <div className={`absolute left-1 top-1 bg-white w-6 h-6 rounded-full shadow-md transition-all duration-300 ease-in-out transform ${
            checked ? 'translate-x-6 scale-110' : 'translate-x-0 scale-100'
          }`}></div>
        </div>
        <span className="ml-3 text-sm text-gray-600 transition-colors duration-200 group-hover:text-gray-800">{label}</span>
      </label>
    );
  };

  return (
    <>
      <main className="p-6 pb-24">
        {/* 제목 입력 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            <FileText className="w-4 h-4 inline mr-2" />
            태스크 제목
          </label>
          <input
            type="text"
            value={formData.title}
            onChange={(e) => setField('title', e.target.value)}
            placeholder="태스크 제목을 입력하세요"
            className={`w-full px-4 py-3 rounded-lg border ${
              errors.title ? 'border-red-500' : 'border-gray-300'
            } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
          />
          {errors.title && <p className="text-red-500 text-sm mt-1">{errors.title}</p>}
        </div>

        {/* 설명 입력 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            설명 (선택사항)
          </label>
          <textarea
            value={formData.description}
            onChange={(e) => setField('description', e.target.value)}
            placeholder="태스크에 대한 설명을 입력하세요"
            rows={3}
            className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
          />
        </div>

        {/* 프로젝트 선택 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            <Folder className="w-4 h-4 inline mr-2" />
            프로젝트
          </label>
          <select
            value={formData.projectId || ''}
            onChange={(e) => setField('projectId', e.target.value ? Number(e.target.value) : null)}
            className={`w-full px-4 py-3 rounded-lg border ${
              errors.projectId ? 'border-red-500' : 'border-gray-300'
            } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
          >
            <option value="">프로젝트를 선택하세요</option>
            <option value="1">웹 애플리케이션 개발</option>
            <option value="2">모바일 앱 개발</option>
            <option value="3">데이터 분석 프로젝트</option>
          </select>
          {errors.projectId && <p className="text-red-500 text-sm mt-1">{errors.projectId}</p>}
        </div>

        {/* 우선순위 선택 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            <Flag className="w-4 h-4 inline mr-2" />
            우선순위
          </label>
          <div className="grid grid-cols-3 gap-2">
            {taskPriorityOptions.map((priority) => (
              <button
                key={priority.value}
                type="button"
                onClick={() => setField('priority', priority.value)}
                className={`p-3 rounded-lg border-2 transition-all ${
                  formData.priority === priority.value
                    ? `border-current ${priority.textColor} ${priority.bgColor}`
                    : 'border-gray-200 text-gray-600 hover:border-gray-300'
                }`}
              >
                <div className="text-center">
                  <div className={`w-3 h-3 rounded-full mx-auto mb-1`} style={{ backgroundColor: priority.color }} />
                  <span className="text-sm font-medium">{priority.label}</span>
                </div>
              </button>
            ))}
          </div>
          {errors.priority && <p className="text-red-500 text-sm mt-1">{errors.priority}</p>}
        </div>

        {/* 상태 선택 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            상태
          </label>
          <select
            value={formData.status}
            onChange={(e) => setField('status', e.target.value)}
            className={`w-full px-4 py-3 rounded-lg border ${
              errors.status ? 'border-red-500' : 'border-gray-300'
            } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
          >
            {taskStatusOptions.map((status) => (
              <option key={status.value} value={status.value}>
                {status.label}
              </option>
            ))}
          </select>
          {errors.status && <p className="text-red-500 text-sm mt-1">{errors.status}</p>}
        </div>

        {/* 요일 라벨 선택 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            <Tag className="w-4 h-4 inline mr-2" />
            Day Label (선택사항)
          </label>
          <div className="grid grid-cols-4 gap-2">
            <button
              type="button"
              onClick={() => setField('dayLabel', null)}
              className={`p-2 rounded-lg border-2 transition-all ${
                formData.dayLabel === null
                  ? 'border-blue-500 bg-blue-50 text-blue-700'
                  : 'border-gray-200 text-gray-600 hover:border-gray-300'
              }`}
            >
              <span className="text-sm">없음</span>
            </button>
            {dayLabelOptions.map((day) => (
              <button
                key={day.value}
                type="button"
                onClick={() => setField('dayLabel', day.value)}
                className={`p-2 rounded-lg border-2 transition-all ${
                  formData.dayLabel === day.value
                    ? 'border-blue-500 bg-blue-50 text-blue-700'
                    : 'border-gray-200 text-gray-600 hover:border-gray-300'
                }`}
              >
                <span className="text-sm">{day.short}</span>
              </button>
            ))}
          </div>
        </div>

        {/* 스케줄 설정 */}
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-gray-800">
            <Calendar className="w-5 h-5 inline mr-2" />
            스케줄 설정
          </h3>

          {/* 시작 날짜/시간 */}
          <div className="bg-gray-50 p-4 rounded-lg space-y-3">
            <h4 className="font-medium text-gray-700">시작</h4>

            <div>
              <label className="block text-sm text-gray-600 mb-1">시작 날짜</label>
              <input
                type="date"
                value={formData.schedule.startDate}
                onChange={(e) => setField('schedule.startDate', e.target.value)}
                className={`w-full px-3 py-2 rounded-lg border ${
                  errors['schedule.startDate'] ? 'border-red-500' : 'border-gray-300'
                } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
              />
              {errors['schedule.startDate'] && <p className="text-red-500 text-xs mt-1">{errors['schedule.startDate']}</p>}
            </div>

            <div className="space-y-3">
              <ToggleSwitch
                checked={formData.schedule.startTimeEnabled}
                onChange={(e) => handleTimeEnabledChange('start', e.target.checked)}
                label="시작 시간 설정"
              />

              {formData.schedule.startTimeEnabled && (
                <div className="pl-2">
                  <input
                    type="time"
                    value={formData.schedule.startTime}
                    onChange={(e) => setField('schedule.startTime', e.target.value)}
                    className={`w-full px-3 py-2 rounded-lg border ${
                      errors['schedule.startTime'] ? 'border-red-500' : 'border-gray-300'
                    } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
                  />
                  {errors['schedule.startTime'] && <p className="text-red-500 text-xs mt-1">{errors['schedule.startTime']}</p>}
                </div>
              )}
            </div>
          </div>

          {/* 마감 날짜/시간 */}
          <div className="bg-gray-50 p-4 rounded-lg space-y-3">
            <h4 className="font-medium text-gray-700">마감</h4>

            <div>
              <label className="block text-sm text-gray-600 mb-1">마감 날짜</label>
              <input
                type="date"
                value={formData.schedule.dueDate}
                onChange={(e) => setField('schedule.dueDate', e.target.value)}
                className={`w-full px-3 py-2 rounded-lg border ${
                  errors['schedule.dueDate'] ? 'border-red-500' : 'border-gray-300'
                } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
              />
              {errors['schedule.dueDate'] && <p className="text-red-500 text-xs mt-1">{errors['schedule.dueDate']}</p>}
            </div>

            <div className="space-y-3">
              <ToggleSwitch
                checked={formData.schedule.dueTimeEnabled}
                onChange={(e) => handleTimeEnabledChange('due', e.target.checked)}
                label="마감 시간 설정"
              />

              {formData.schedule.dueTimeEnabled && (
                <div className="pl-2">
                  <input
                    type="time"
                    value={formData.schedule.dueTime}
                    onChange={(e) => setField('schedule.dueTime', e.target.value)}
                    className={`w-full px-3 py-2 rounded-lg border ${
                      errors['schedule.dueTime'] ? 'border-red-500' : 'border-gray-300'
                    } focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
                  />
                  {errors['schedule.dueTime'] && <p className="text-red-500 text-xs mt-1">{errors['schedule.dueTime']}</p>}
                </div>
              )}
            </div>
          </div>
        </div>

        {/* 색상 선택 */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            <Palette className="w-4 h-4 inline mr-2" />
            태스크 색상
          </label>
          <div className="grid grid-cols-6 gap-3">
            {taskColors.map((color) => (
              <button
                key={color.id}
                type="button"
                onClick={() => setField('colorId', color.id)}
                className={`w-12 h-12 rounded-full border-4 transition-all ${
                  formData.colorId === color.id
                    ? 'border-gray-800 scale-110'
                    : 'border-gray-200 hover:border-gray-400'
                }`}
                style={{ backgroundColor: color.color }}
                title={color.name}
              />
            ))}
          </div>
          {errors.colorId && <p className="text-red-500 text-sm mt-1">{errors.colorId}</p>}
        </div>
      </main>

      <StickyFooter>
        <button
          onClick={handleSubmit}
          disabled={loading}
          className={`w-full py-4 rounded-2xl font-bold text-lg transition-all ${
            loading
              ? 'bg-gray-400 cursor-not-allowed'
              : 'bg-gradient-to-r from-blue-600 to-purple-600 hover:shadow-xl active:scale-95'
          } text-white`}
        >
          {loading ? '태스크 생성 중...' : '태스크 생성'}
        </button>
      </StickyFooter>
    </>
  );
};

export default TaskForm;