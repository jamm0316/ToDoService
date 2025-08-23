-- ================================================================================= --
-- Seed Data for ToDoService-GreenCat (initial_data.sql)
-- ================================================================================= --

-- --------------------------------------------------------------------------
-- COLOR
-- --------------------------------------------------------------------------
INSERT INTO color (id, name, hex_code)
VALUES
  (1, 'RED',   '#FF0000'),
  (2, 'BLUE',  '#0000FF'),
  (3, 'GREEN', '#00FF00');

-- --------------------------------------------------------------------------
-- PROJECT
-- --------------------------------------------------------------------------
INSERT INTO project (
  id, color_id, name, status, description,
  is_public, visibility, start_date, end_date, actual_end_date,
) VALUES
  (1, 1, '프로젝트 A', 'PLANNING', '캘린더 기반 공유 프로젝트',
   TRUE, 'PRIVATE', '2025-08-01', '2025-12-31', NULL,
   ),
  (2, 2, '프로젝트 B', 'IN_PROGRESS', '검색/요약 기능 검증',
   TRUE, 'PRIVATE', '2025-07-01', '2025-09-30', NULL,
   );

-- --------------------------------------------------------------------------
-- TASK
-- --------------------------------------------------------------------------
INSERT INTO task (
  id, project_id, color_id, title, description,
  priority, status, day_label,
  start_date, start_time, start_time_enabled,
  due_date, due_time, due_time_enabled,
) VALUES
  (1, 1, 1, '프로젝트 구조 정리', '도메인/패키지 구조 정리',
   'HIGH', 'PLANNING', 'DAY1',
   '2025-08-10', '09:00:00', TRUE,
   '2025-08-30', '18:00:00', TRUE,
   ),

  (2, 1, 2, '색상 시스템 적용', '프로젝트 색상 분류 시스템 적용',
   'MEDIUM', 'PLANNING', 'DAY2',
   '2025-08-12', '10:00:00', FALSE,
   '2025-09-05', NULL, FALSE,
   ),

  (3, 2, 3, '검색 API 연동', '프론트엔드와 백엔드 검색 API 연동',
   'HIGH', 'IN_PROGRESS', 'DAY3',
   '2025-08-15', NULL, FALSE,
   '2025-08-28', '17:00:00', TRUE,
   );
