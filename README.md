# 📌 프로젝트 & 할 일(Task) 관리 서비스

## 📖 앱 설명
	•	프로젝트와 할 일(Task)을 생성/조회/수정/삭제(CRUD)할 수 있는 관리 서비스
	•	프로젝트 진행률(progress) 자동 계산 및 요약 제공
	•	색상(Color)을 이용한 프로젝트 분류 기능 제공
	•	프로젝트 이름 기반 검색 API 제공
### 현재 가능한 기능
1. 검색기능
   - 검색어 입력으로 프로젝트 찾기 가능
   - <img width="365" height="57" alt="image" src="https://github.com/user-attachments/assets/18614778-3b0b-4c0c-9b0b-cafdba45cfff" />

2. 프로젝트 생성기능
   - [+] 네모 + 버튼을 눌러 project 생성가능
   - <img width="369" height="334" alt="image" src="https://github.com/user-attachments/assets/f6963ba4-1095-42ad-87cb-e7befdca4ee5" />

3. 프로젝트 수정 기능
   - 프로젝트 단건 조회 후 수정 기능
   - <img width="455" height="625" alt="image" src="https://github.com/user-attachments/assets/276d1296-a6ff-4ed1-846a-5a897fefd2e8" />

4. 할 일 생성 기능
   - 하단 nav의 +버튼 클릭으로 할 일 생성 가능
   - <img width="447" height="79" alt="image" src="https://github.com/user-attachments/assets/65077034-3fef-416e-9ce5-ff4bb45b1547" />
 

## 🛠 소스 빌드 및 실행 방법

> 💡 Tip: 실행 전에 src/main/resources/application.properties를 반드시 작성해야 정상 실행됩니다!

### 0) 프로젝트 클론

### 1) 백엔드 (Spring Boot)

#### (1) application.properties 생성 (필수)

경로: src/main/resources/application.properties
아래 예시에서 {} 값만 본인 환경에 맞게 수정하세요.
```yaml
spring.application.name=greencatsoftware

# ==== Local DB ====
spring.datasource.url=jdbc:mysql://{DB_HOST}:{DB_PORT}/{DB_NAME}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username={DB_USERNAME}
spring.datasource.password={DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ==== JPA ====
spring.jpa.hibernate.ddl-auto={DDL_AUTO}   # ex) none | validate | update | create | create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

# ==== Swagger ====
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```
	•	{DB_HOST}: 예) localhost
	•	{DB_PORT}: 예) 3306
	•	{DB_NAME}: 예) greencatsoftware-db
	•	{DB_USERNAME}: 예) evan
	•	{DB_PASSWORD}: 실제 비밀번호
	•	{DDL_AUTO}: 최초엔 create, 테이블 생성 이후엔 none 또는 validate 추천

#### (2) DB 스키마 적용 & 기초 데이터 로드

아래 SQL 파일을 저장소에 포함하고, 최초 1회 실행하세요.
	•	스키마 파일: db/schema/schema.sql
	•	기초 데이터(백업) 파일: db/seed/initial_data.sql

MySQL 콘솔 예시:

1) 스키마 생성
```bash
mysql -u {DB_USERNAME} -p {DB_NAME} < db/schema/schema.sql
```

2) 기초데이터 입력
```bash
mysql -u {DB_USERNAME} -p {DB_NAME} < db/seed/initial_data.sql
```

db/schema/schema.sql
```sql
-- ================================================================================= --
--                              ToDoService-GreenCat                                 --
-- ================================================================================= --

-- --------------------------------------------------------------------------------- --
-- Table `color`  (프로젝트 색상 분류)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `color` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `hex_code` VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_color_name` (`name`),
    UNIQUE INDEX `UK_color_hex_code` (`hex_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------------------------------- --
-- Table `project`  (최상위 작업 단위)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `project` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `color_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'PLANNING',
    `description` TEXT NULL,
    `is_public` BOOLEAN NOT NULL DEFAULT TRUE,
    `visibility` VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    `start_date` DATE NULL,
    `end_date` DATE NULL,
    `actual_end_date` DATE NULL,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    INDEX `FK_project_color` (`color_id`),
    CONSTRAINT `FK_project_color`
        FOREIGN KEY (`color_id`)
        REFERENCES `color` (`id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------------------------------- --
-- Table `task`  (프로젝트 하위 할 일)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `project_id` BIGINT NOT NULL,
    `priority` VARCHAR(10) NOT NULL DEFAULT 'HIGH',
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    `day_label` VARCHAR(255) NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'PLANNING',
    `start_date` DATE NULL,
    `start_time` TIME NULL,
    `start_time_enabled` BOOLEAN NOT NULL DEFAULT FALSE,
    `due_date` DATE NULL,
    `due_time` TIME NULL,
    `due_time_enabled` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    INDEX `FK_task_project` (`project_id`),
    CONSTRAINT `FK_task_project`
        FOREIGN KEY (`project_id`)
        REFERENCES `project` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

db/seed/initial_data.sql
```sql
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
```

#### (3) 빌드 & 실행
```bash
./gradlew build
./gradlew bootRun
```

> 💡 Swagger UI: http://localhost:8080/swagger-ui.html

⸻

### 2) 프론트엔드 (React/Vite)

#### (1) 디렉토리 이동
```bash
cd frontend
```
#### (2) 패키지 설치
```bash
npm install
```
#### (3) 개발 서버 실행
```bash
npm run dev
```
> 💡 기본 접속: http://localhost:5173

필요 시 .env 파일에 VITE_API_BASE_URL=http://localhost:8080 등을 설정해 백엔드와 연동하세요.


실행 순서 요약
	1.	git clone으로 소스 내려받기
	2.	application.properties 작성
	3.	DB 스키마/시드 로드: schema.sql → initial_data.sql
	4.	백엔드: ./gradlew build && ./gradlew bootRun
	5.	프론트엔드: cd frontend && npm install && npm run dev


## ⚙️ Tech Stack (주력 라이브러리)

### 기술	설명	사용 이유
1. Spring Boot (Web, Data JPA, Validation)	Java 기반 웹 애플리케이션 프레임워크	REST API 개발 표준, 자동 설정/의존성 관리로 생산성↑
2. Springdoc OpenAPI (Swagger)	API 명세 자동화 + UI 테스트 제공	문서화 자동화, FE 협업 용이
3. Lombok	Getter/Setter/생성자 자동 생성	반복 코드 제거, 가독성 향상
4. MySQL Connector/J	MySQL 연결 JDBC 드라이버	운영 환경 DB 연결
5. H2 Database	인메모리 DB	테스트 환경 분리, 빠른 단위 테스트


## 🔐 API 명세
> 💡 애플리케이션 실행 후 Swagger UI에서 전체 API 확인 및 테스트 가능
> http://localhost:8080/swagger-ui.html

## 추가 기능 API
	•	프로젝트 요약/진행률 조회
	•	GET /api/v1/project/summary
	•	설명: 각 프로젝트의 태스크 완료율을 계산해 진행률 포함 요약 정보 반환
	•	프로젝트 이름 검색
	•	GET /api/v1/project/search?keyword={검색어}
	•	설명: 이름에 키워드가 포함된 프로젝트 목록 검색


## 🧪 테스트 케이스
	•	프레임워크: JUnit 5
	•	경로: src/test/java/com/todoservice/greencatsoftware/domain/
	•	도메인별 테스트 포함:
	•	Color / Project / Task (엔티티, 서비스, 리포지토리 계층 검증)
	•	명령:
```bash
./gradlew test
```

## 🚀 주요 기능 요약
	1.	프로젝트 CRUD – 생성/조회/수정/삭제
	2.	태스크(Task) CRUD – 생성/조회/수정/삭제
	3.	색상 관리 – 프로젝트 색상 분류
	4.	프로젝트 진행률 – 하위 태스크 완료율 기반 계산
	5.	검색 API – 키워드 기반 프로젝트 검색
