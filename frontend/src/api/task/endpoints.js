const VERSION = 'v1';
const DOMAIN = 'task'
export const ENDPOINTS = {
  TASKS: `${VERSION}/${DOMAIN}`,
  SUMMARY_TASKS: `${VERSION}/${DOMAIN}/summary`,
  TODAY_TASKS: `${VERSION}/${DOMAIN}/today`,
  TASK_BY_ID: (id) => `${VERSION}/${DOMAIN}/${id}`,
}