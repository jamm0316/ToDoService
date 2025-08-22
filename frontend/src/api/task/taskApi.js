import {ENDPOINTS} from "./endpoints.js";
import apiClient from "/src/api/client.js";

export const taskApi = {
  createTask: async (projectData) => {
    const response = await apiClient.post(ENDPOINTS.TASKS, projectData);
    return response.data;
  },

  summaryTask: async () => {
    const response = await apiClient.get(ENDPOINTS.SUMMARY_TASKS);
    return response.data.result;
  }
}