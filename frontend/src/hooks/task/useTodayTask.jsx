import {useEffect, useState} from "react";
import {taskApi} from "/src/api/task/taskApi.js";

const useTodayTask = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    let ignore = false;
    (async () => {
      try {
        setLoading(true);
        const response = await taskApi.todayTask()
        if (!ignore) {
          setData(response);
        }
      } catch (err) {
        if (!ignore) setError(err);
      } finally {
        if (!ignore) setLoading(false);
      }
    })();
    return () => {
      ignore = true
    };
  }, []);
  return {data, loading, error};
};

export default useTodayTask;