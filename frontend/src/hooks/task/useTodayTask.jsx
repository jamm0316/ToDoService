import {useCallback, useEffect, useRef, useState} from "react";
import {taskApi} from "/src/api/task/taskApi.js";

const useTodayTask = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const isMountedRef = useRef(true);

  const refetch = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await taskApi.todayTask();
      if (!isMountedRef.current) return;
      setData(Array.isArray(response) ? response : []);
    } catch (err) {
      if (!isMountedRef.current) return;
      setError(err);
    } finally {
      if (!isMountedRef.current) return;
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    isMountedRef.current = true;
    // 마운트 시 자동 로드
    refetch();
    return () => {
      isMountedRef.current = false;
    };
  }, [refetch]);

  return { data, loading, error, refetch, setData };
};


export default useTodayTask;