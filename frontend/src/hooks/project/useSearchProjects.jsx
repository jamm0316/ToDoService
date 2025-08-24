import { useState, useEffect, useRef } from "react";
import {projectApi} from "/src/api/project/projectApi.js";

export default function useSearchProjects(initialKeyword = "") {
  const [keyword, setKeyword] = useState(initialKeyword);
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const clear = () => setKeyword("");
  const timerRef = useRef(null);

  useEffect(() => {
    if (timerRef.current) clearTimeout(timerRef.current);

    if (!keyword || !keyword.trim()) {
      setData([]);
      setError("");
      return;
    }

    timerRef.current = setTimeout(async () => {
      setLoading(true);
      setError("");
      try {
        const result = await projectApi.searchProject(keyword);
        setData(result);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    }, 300);

    return () => clearTimeout(timerRef.current);
  }, [keyword]);

  return { keyword, setKeyword, data, loading, error, clear };
}