import React from 'react';
import { Folder, Plus, RefreshCw } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '/src/router/routes.js';
import useSummaryProject from '/src/hooks/project/useSummaryProject.jsx'

const ProjectSelector = ({
                           value,
                           onChange,
                           error,
                           className = "",
                           showCreateButton = true
                         }) => {
  const navigate = useNavigate();
  const { data: projects, loading, error: projectsError } = useSummaryProject();

  const handleCreateProject = () => {
    navigate(ROUTES.PROJECT.CREATE);
  };

  if (loading) {
    return (
      <div className={className}>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          <Folder className="w-4 h-4 inline mr-2" />
          프로젝트
        </label>
        <div className="w-full px-4 py-3 rounded-lg border border-gray-300 bg-gray-50 flex items-center">
          <RefreshCw className="w-4 h-4 animate-spin mr-2 text-gray-400" />
          <span className="text-gray-500">프로젝트 목록을 불러오는 중...</span>
        </div>
      </div>
    );
  }

  if (projectsError) {
    return (
      <div className={className}>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          <Folder className="w-4 h-4 inline mr-2" />
          프로젝트
        </label>
        <div className="w-full px-4 py-3 rounded-lg border border-red-300 bg-red-50">
          <p className="text-red-600 text-sm">프로젝트 목록을 불러오는데 실패했습니다.</p>
          <p className="text-red-600 text-sm">페이지를 새로고침해주세요.</p>
        </div>
      </div>
    );
  }

  return (
    <div className={className}>
      <div className="flex items-center justify-between mb-2">
        <label className="block text-sm font-medium text-gray-700">
          <Folder className="w-4 h-4 inline mr-2" />
          프로젝트
        </label>
        {showCreateButton && (
          <button
            type="button"
            onClick={handleCreateProject}
            className="flex items-center text-sm text-blue-600 hover:text-blue-800 transition-colors"
          >
            <Plus className="w-4 h-4 mr-1" />
            새 프로젝트
          </button>
        )}
      </div>

      <select
        value={value || ''}
        onChange={(e) => onChange(e.target.value ? Number(e.target.value) : null)}
        className={`w-full px-4 py-3 rounded-lg border transition-all ${
          error ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
        } focus:ring-2 focus:border-transparent`}
      >
        <option value="">프로젝트를 선택하세요</option>
        {projects.map((project) => (
          <option key={project.id} value={project.id}>
            {project.name}
          </option>
        ))}
      </select>

      {error && <p className="text-red-500 text-sm mt-1">{error}</p>}

      {projects.length === 0 && !loading && (
        <p className="text-gray-500 text-sm mt-1">
          생성된 프로젝트가 없습니다.
          {showCreateButton && (
            <button
              type="button"
              onClick={handleCreateProject}
              className="text-blue-600 hover:text-blue-800 ml-1 underline"
            >
              새 프로젝트를 생성해보세요.
            </button>
          )}
        </p>
      )}
    </div>
  );
};

export default ProjectSelector;