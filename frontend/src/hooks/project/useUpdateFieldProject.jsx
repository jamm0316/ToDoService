import React from 'react';
import {projectApi} from "/src/api/project/projectApi.js";

const useUpdateFieldProject = () => {
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState(null);

  const updateFieldProject = async (projectId, projectData) => {
    try {
      setLoading(true);
      setError(null);

      const result = await projectApi.updateProjectFieldById(projectId, projectData);
      return {success: true, data: result};
    } catch (err) {
      const errorMessage = err.response?.data?.message || '프로젝트 수정에 실패했습니다';
      setError(errorMessage);
      return {success: false, error: errorMessage};
    } finally {
      setLoading(false);
    }
  };

  return {
    updateFieldProject, loading, error,
    clearError: () => setError(null)
  };
};

export default useUpdateFieldProject;