import { useState } from 'react';
import {taskApi} from "/src/api/task/taskApi.js";

const useCreateTask = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const createTask = async (taskData) => {
    try {
      setLoading(true);
      setError(null);

      console.log('Creating task with data:', taskData);

      const result = await taskApi.createTask(taskData);
      return {success: true, data: result};
    } catch (err) {
      const errorMessage = err.response?.data?.message || '프로젝트 생성에 실패했습니다';
      setError(errorMessage);
      return {success: false, error: errorMessage};
    } finally {
      setLoading(false);
    }
  };

  return {
    createTask, loading, error,
    clearError: () => setError(null)
  };
};

export default useCreateTask;