import { useEffect, useState } from "react";
import { projectApi } from "/src/api/project/projectApi.js";

const useDetailProject = (projectId) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchProject = async (id) => {
    if (!id) return;

    try {
      setLoading(true);
      setError(null);
      const response = await projectApi.getProjectById(id);
      setData(response);
    } catch (err) {
      setError(err);
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProject(projectId);
  }, [projectId]);

  const refetch = () => {
    fetchProject(projectId);
  };

  return { data, loading, error, refetch };
};

export default useDetailProject;