import { useEffect, useState } from "react";
import {taskApi} from "/src/api/task/taskApi.js";

const useDetailTask = (taskId) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchTask = async (id) => {
    if (!id) return;

    try {
      setLoading(true);
      setError(null);
      const response = await taskApi.getTaskById(id);
      setData(response);
    } catch (err) {
      setError(err);
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTask(taskId);
  }, [taskId]);

  const refetch = () => {
    fetchTask(taskId);
  };

  return { data, loading, error, refetch };
};

export default useDetailTask;