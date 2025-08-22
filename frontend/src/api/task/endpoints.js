const VERSION = 'v1';
const DOMAIN = 'task'
export const ENDPOINTS = {
  TASKS: `${VERSION}/${DOMAIN}`,
  SUMMARY_TASKS: `${VERSION}/${DOMAIN}/summary`,
  TASK_BY_ID: (id) => `${VERSION}/${DOMAIN}/${id}`,
}