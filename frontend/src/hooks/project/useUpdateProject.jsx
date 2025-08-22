import { useState } from 'react';
import { projectApi } from '/src/api/project/projectApi.js';

const useUpdateProject = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const updateProject = async (projectId, projectData) => {
    try {
      setLoading(true);
      setError(null);

      const response = await projectApi.updateProject(projectId, projectData);

      return {
        success: true,
        data: response
      };
    } catch (err) {
      setError(err);
      return {
        success: false,
        error: err
      };
    } finally {
      setLoading(false);
    }
  };

  return {
    updateProject,
    loading,
    error
  };
};

export default useUpdateProject;