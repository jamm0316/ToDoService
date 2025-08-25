import React from 'react';
import {taskApi} from "/src/api/task/taskApi.js";

const useUpdateFieldTask = () => {
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState(null);

  const updateFieldTask = async (taskId, taskData) => {
    try {
      setLoading(true);
      setError(null);

      const result = await taskApi.updateTaskFieldById(taskId, taskData)
      return {success: true, data: result};
    } catch (err) {
      const errorMessage = err.response?.data?.message || '할 일 수정에 실패했습니다';
      setError(errorMessage);
      return {success: false, error: errorMessage};
    } finally {
      setLoading(false);
    }
  };

  return {
    updateFieldTask, loading, error,
    clearError: () => setError(null)
  };
};

export default useUpdateFieldTask;