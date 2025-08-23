# 📌 프로젝트 & 할 일(Task) 관리 서비스

## 📖 앱 설명
- 프로젝트와 할 일(Task)을 생성/조회/수정/삭제(CRUD)할 수 있는 관리 서비스
- 프로젝트 진행률(progress) 자동 계산 및 요약 제공
- 색상(Color)을 이용한 프로젝트 분류 기능 제공
- 이름 기반 검색 API 제공

---

## 🛠 소스 빌드 및 실행 방법
> 💡 Tip: 실행 전 application.properties를 반드시 작성해야 정상 실행됩니다!

### 1. 소스 빌드
```bash
./gradlew build
```

### 2. DB 스키마
	•	Spring Data JPA의 ddl-auto 옵션에 따라 애플리케이션 실행 시 엔티티 기반으로 자동 생성됨
	•	별도의 SQL 스키마 파일은 제공되지 않음

### 3. 기초 데이터
	•	초기 데이터 백업 파일 없음 → 필요시 수동 Insert

### 4. 실행 방법

(1) application.application 생성 (필수)

src/main/resources/application.yml 경로에 아래 예시대로 작성 (본인 DB 환경에 맞게 수정):

```java
spring.application.name: greencatsoftware

### Local DB ###
spring.datasource.url: jdbc:mysql://localhost:3306/
spring.datasource.username: {사용자이름}
spring.datasource.password: {DB 비밀번호}
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver

### JPA ###
spring.jpa.hibernate.ddl-auto: none  #최초 create로 DB 테이블 생성해야함
spring.jpa.properties.hibernate.format_sql: true
spring.jpa.show-sql: true

### Swagger-ui ###
springdoc.api-docs.enabled: true
springdoc.api-docs.path: /v3/api-docs
springdoc.swagger-ui.path: /swagger-ui.html
```

(2) 애플리케이션 실행
```bash
./gradlew bootRun
```

⸻

## ⚙️ Tech Stack (주력 라이브러리)

기술	설명	사용 이유
1. Spring Boot (Web, Data JPA, Validation)	Java 기반 웹 애플리케이션 프레임워크	REST API 개발 표준, 생산성/안정성
2. Springdoc OpenAPI (Swagger)	API 명세 자동화 + UI 테스트 제공	문서화 자동화 및 FE 협업 용이
3. Lombok	Getter, Setter, 생성자 자동 생성	반복코드 제거, 가독성 향상
4. MySQL Connector/J	MySQL 연결용 JDBC 드라이버	운영 환경 DB 연결
5. H2 Database	인메모리 DB	테스트 환경 분리, 빠른 단위 테스트


⸻

## 🔐 API 명세
	•	실행 후 http://localhost:8080/swagger-ui.html 접속 시 전체 API 확인 가능

추가 기능 API

Method	Endpoint	설명
`GET	/api/v1/project/summary`	프로젝트별 진행률 포함 요약 조회
`GET	/api/v1/project/search?keyword={검색어}`	프로젝트 이름 기반 검색


⸻

## 🧪 테스트 케이스
	•	프레임워크: JUnit 5
	•	경로: src/test/java/com/todoservice/greencatsoftware/domain/
	•	도메인별 테스트 포함:
	•	Color
	•	Project
	•	Task
(엔티티, 서비스, 리포지토리 계층별 검증)

⸻

## 📁 디렉토리 구조 (예시)

src
 ├─ main
 │   ├─ java/com/todoservice/greencatsoftware
 │   │    ├─ domain
 │   │    │    ├─ project
 │   │    │    ├─ task
 │   │    │    └─ color
 │   │    ├─ common
 │   │    └─ ...
 │   └─ resources
 │        └─ application.yml (생성 필요)
 └─ test/java/com/todoservice/greencatsoftware
      └─ domain


⸻

## 🚀 주요 기능

### 기능	설명
1. 프로젝트 CRUD	생성/조회/수정/삭제
2. 태스크(Task) CRUD	생성/조회/수정/삭제
3. 색상 관리	프로젝트 색상 분류
4. 프로젝트 진행률	하위 태스크 완료율 기반 계산
5. 검색 API	키워드 기반 프로젝트 검색


⸻

### 📄 추가 문서
	•	상세 기획 및 ERD: (📘 Notion / Wiki 링크 추가 예정)
	•	Trouble Shooting: (🔗 링크 추가 가능)

⸻
