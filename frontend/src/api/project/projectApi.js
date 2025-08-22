import {ENDPOINTS} from "./endpoints.js";
import apiClient from "/src/api/client.js";

export const projectApi = {
  createProject: async (projectData) => {
    const response = await apiClient.post(ENDPOINTS.PROJECT, projectData);
    return response.data;
  },
  
  summaryProject: async () => {
    const response = await apiClient.get(ENDPOINTS.SUMMARY_PROJECTS);
    return response.data.result;
  }
}