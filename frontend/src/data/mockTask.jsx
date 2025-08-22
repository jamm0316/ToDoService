// 프로젝트 목 데이터 (태스크 생성 시 프로젝트 선택용)
export const mockProjects = [
  {
    id: 1,
    name: '웹 애플리케이션 개발',
    status: 'IN_PROGRESS',
    colorId: 1
  },
  {
    id: 2,
    name: '모바일 앱 개발',
    status: 'PLANNING',
    colorId: 2
  },
  {
    id: 3,
    name: '데이터 분석 프로젝트',
    status: 'IN_PROGRESS',
    colorId: 3
  },
  {
    id: 4,
    name: 'UI/UX 리디자인',
    status: 'COMPLETED',
    colorId: 4
  },
  {
    id: 5,
    name: 'API 서버 구축',
    status: 'IN_PROGRESS',
    colorId: 5
  }
];

// 태스크 목 데이터
export const mockTasks = [
  {
    id: 1,
    title: 'API 문서 작성',
    status: '진행중',
    priority: 'HIGH',
    dueDate: '2025-08-25',
    dayLabel: '🌱 아침',
    colorId: 1,
    progress: 75,
  }
];

// 담당자 목 데이터 (태스크 할당용)
export const mockAssignees = [
  { id: 1, name: '김개발', email: 'dev.kim@company.com', role: 'DEVELOPER' },
  { id: 2, name: '박설계', email: 'design.park@company.com', role: 'ARCHITECT' },
  { id: 3, name: '이디자인', email: 'ui.lee@company.com', role: 'DESIGNER' },
  { id: 4, name: '최테스트', email: 'test.choi@company.com', role: 'QA' },
  { id: 5, name: '강데브옵스', email: 'devops.kang@company.com', role: 'DEVOPS' },
  { id: 6, name: '신보안', email: 'security.shin@company.com', role: 'SECURITY' },
  { id: 7, name: '한성능', email: 'performance.han@company.com', role: 'DEVELOPER' },
  { id: 8, name: '문번역', email: 'translate.moon@company.com', role: 'TRANSLATOR' }
];

// 태스크 생성을 위한 기본 템플릿들
export const taskTemplates = [
  {
    title: 'API 개발',
    description: 'REST API 엔드포인트 개발',
    priority: 'HIGH',
    status: 'IN_PROGRESS',
    dayLabel: null
  },
  {
    title: '데이터베이스 작업',
    description: '테이블 설계 및 쿼리 최적화',
    priority: 'MEDIUM',
    status: 'IN_PROGRESS',
    dayLabel: null
  },
  {
    title: 'UI 구현',
    description: '사용자 인터페이스 컴포넌트 개발',
    priority: 'MEDIUM',
    status: 'IN_PROGRESS',
    dayLabel: null
  },
  {
    title: '테스트 작성',
    description: '단위 테스트 및 통합 테스트 케이스 작성',
    priority: 'LOW',
    status: 'IN_PROGRESS',
    dayLabel: null
  },
  {
    title: '문서 작성',
    description: '기술 문서 및 사용자 가이드 작성',
    priority: 'LOW',
    status: 'IN_PROGRESS',
    dayLabel: null
  }
];

// 태스크 상태별 통계 (대시보드용)
export const taskStatusStats = {
  TODO: mockTasks.filter(t => t.status === 'IN_PROGRESS').length,
  IN_PROGRESS: mockTasks.filter(t => t.status === 'IN_PROGRESS').length,
  DONE: mockTasks.filter(t => t.status === 'DONE').length,
  CANCELLED: mockTasks.filter(t => t.status === 'CANCELLED').length
};

// 우선순위별 통계
export const taskPriorityStats = {
  HIGH: mockTasks.filter(t => t.priority === 'HIGH').length,
  MEDIUM: mockTasks.filter(t => t.priority === 'MEDIUM').length,
  LOW: mockTasks.filter(t => t.priority === 'LOW').length
};

// 최근 태스크 활동 (최근 활동 섹션용)
export const mockTaskActivity = [
  {
    id: 1,
    type: 'task_created',
    taskId: 6,
    taskTitle: '사용자 인증 구현',
    projectName: 'API 서버 구축',
    assignee: '신보안',
    timestamp: '2025-08-21T16:00:00Z',
    message: '새로운 태스크가 생성되었습니다.'
  },
  {
    id: 2,
    type: 'task_completed',
    taskId: 8,
    taskTitle: '문서 번역',
    projectName: 'UI/UX 리디자인',
    assignee: '문번역',
    timestamp: '2025-08-21T15:30:00Z',
    message: '태스크가 완료되었습니다.'
  },
  {
    id: 3,
    type: 'task_updated',
    taskId: 1,
    taskTitle: 'API 문서 작성',
    projectName: '웹 애플리케이션 개발',
    assignee: '김개발',
    timestamp: '2025-08-22T14:30:00Z',
    message: '태스크 진행률이 75%로 업데이트되었습니다.'
  }
];