import {post} from "axios";
import {ENDPOINTS} from "src/api/endpoints.js";
import apiClient from "src/api/client.js";

export const projectApi = {
  createProject: async (projectData) => {
    const reponse = await apiClient.post(ENDPOINTS.PROJECT, projectData);
    return reponse.data;
  },
}