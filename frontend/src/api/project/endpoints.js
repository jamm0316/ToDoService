const VERSION = 'v1';
const DOMAIN = 'project'
export const ENDPOINTS = {
  PROJECT: `${VERSION}/${DOMAIN}`,
  SUMMARY_PROJECTS: `${VERSION}/${DOMAIN}/summary`,
  PROJECT_BY_ID: (id) => `${VERSION}/${DOMAIN}/${id}`,
}