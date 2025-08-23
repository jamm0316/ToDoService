import {useCallback, useEffect, useRef, useState} from "react";
import {projectApi} from "/src/api/project/projectApi.js";

const useSummaryProject = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const isMountedRef = useRef(true);

  const refetch = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await projectApi.summaryProject();
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

  return { data, loading, error, refetch, setData }; // setData도 필요하면 노출
};


export default useSummaryProject;