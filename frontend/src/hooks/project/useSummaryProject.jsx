import {useEffect, useState} from "react";
import {projectApi} from "/src/api/project/projectApi.js";

const useSummaryProject = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    let ignore = false;
    (async () => {
      try {
        setLoading(true);
        const response = await projectApi.summaryProject()
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

export default useSummaryProject;