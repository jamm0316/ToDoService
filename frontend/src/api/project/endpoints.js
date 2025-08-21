const VERSION = 'v1';
export const ENDPOINTS = {
  PROJECT: `${VERSION}/project`,
  PROJECT_BY_ID: (id) => `${VERSION}/project/${id}`,
}