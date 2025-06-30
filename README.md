# SSA 쇼핑몰 프로젝트

## 소개
이 프로젝트는 Spring Boot와 JPA, JSP 기반의 온라인 쇼핑몰 웹 애플리케이션입니다.  
회원가입, 로그인, 상품 조회 및 결제, 문의/리뷰, 관리자 기능 등 전형적인 이커머스 기능을 제공합니다.

## 주요 기능
- **회원 관리**: 회원가입, 로그인/로그아웃, 아이디/비밀번호 찾기, 마이페이지, 회원 탈퇴
- **상품 관리**: 상품 목록/상세 조회, 카테고리별 상품, 장바구니, 상품 결제(카카오페이 연동)
- **리뷰/문의**: 상품 리뷰 작성 및 추천, 문의사항 등록/조회
- **관리자 기능**: 상품/회원/문의/환불 관리, 관리자 전용 대시보드
- **결제/환불**: 카카오페이 결제, 결제 내역 조회, 환불 요청 및 처리
- **캐싱 시스템**: ProductImg 캐시로 이미지 조회 성능 최적화
- **상품 옵션**: ProductVariant를 통한 상품 옵션 및 가격 관리

## 폴더 구조
```
src/
  main/
    java/web/ssa/
      controller/   # 웹 요청 처리 (admin, client 등)
      service/      # 비즈니스 로직
      repository/   # DB 접근(JPA)
      entity/       # JPA 엔티티(회원, 상품, 결제 등)
      dto/          # 데이터 전송 객체
      cache/        # 캐싱 시스템 (CategoriesCache, ProductImgCache)
      filter/       # 인증 등 필터
      enumf/        # Enum 타입
      util/         # 유틸리티
      SsaApplication.java # 메인 클래스
    resources/
      application.properties # 환경설정
      static/                # 정적 리소스
      templates/             # (미사용)
    webapp/
      WEB-INF/views/         # JSP 뷰 (로그인, 회원가입, 상품, 관리자 등)
      css/                   # 스타일시트
  test/
    java/web/ssa/
      SsaApplicationTests.java # 테스트 코드
```

## 사용 기술
- Java 17+
- Spring Boot, Spring MVC, Spring Data JPA
- JSP (View)
- MySQL
- Gradle
- Lombok
- 카카오페이 API

## 주요 아키텍처 특징
- **계층 분리**: Controller → Service → Repository 패턴
- **DTO 패턴**: 데이터 전송 객체를 통한 계층 간 데이터 전달
- **캐싱 시스템**: 자주 사용되는 데이터(카테고리, 이미지) 메모리 캐싱
- **결제 시스템**: 카카오페이 연동으로 안전한 결제 처리
- **상품 옵션**: ProductMaster + ProductVariant 구조로 다양한 상품 옵션 지원

## 빌드 및 실행 방법
1. **의존성 설치**
   ```
   ./gradlew build -x test
   ```
2. **애플리케이션 실행**
   ```
   ./gradlew bootRun
   ```
   또는 IDE에서 `SsaApplication.java` 실행

3. **접속**
   - 기본 포트: `http://localhost:8080/`
   - 주요 페이지: `/login`, `/register`, `/shop/products`, `/mypage`, `/admin`

## 주요 화면
- `login.jsp` : 로그인
- `register.jsp` : 회원가입
- `productList.jsp` : 상품 목록 (캐시된 이미지 사용)
- `mypage.jsp` : 마이페이지(결제 내역, 회원정보)
- `Inquiry/list.jsp` : 문의 목록
- `admin/admin.jsp` : 관리자 대시보드
- `admin/productList.jsp` : 관리자 상품 관리
- `admin/userList.jsp` : 관리자 회원 관리

## 캐싱 시스템
- **CategoriesCache**: 카테고리 정보 메모리 캐싱
- **ProductImgCache**: 상품 이미지 URL 메모리 캐싱
  - 애플리케이션 시작 시 모든 이미지 로드
  - DB 조회 없이 빠른 이미지 URL 접근
  - 캐시 갱신/추가/수정/삭제 기능 제공

## 결제 시스템
- **카카오페이 연동**: 안전한 결제 처리
- **단일/복수 상품 결제**: SelectedProductDTO 기반 일관된 처리
- **환불 시스템**: 사용자 요청 → 관리자 승인 → 카카오페이 환불 처리
- **결제 내역 관리**: 사용자별 결제 내역 조회 및 관리

## 테스트
- `src/test/java/web/ssa/SsaApplicationTests.java`  
  (기본 Spring Boot 테스트 코드 포함)

## 기타
- 환경설정은 `src/main/resources/application.properties`에서 관리합니다.
- 카카오페이 연동을 위해 별도의 API Key가 필요합니다.
- DB는 MySQL을 사용하며, 접속 정보는 properties 파일에 설정되어 있습니다.
- 캐싱 시스템으로 성능 최적화가 적용되어 있습니다. 

## 📋 프로젝트 개요
Spring Boot 기반의 온라인 쇼핑몰 프로젝트입니다. 회원 관리, 상품 관리, 리뷰, 문의, 관리자 기능, 카카오페이 결제 등 다양한 기능을 제공합니다.

## ✨ 주요 기능

### 🛍️ 쇼핑몰 기능
- **상품 조회**: 카테고리별 상품 목록 및 상세 정보
- **상품 검색**: 키워드 기반 상품 검색
- **장바구니**: 상품 선택 및 수량 관리
- **결제 시스템**: 카카오페이 연동 결제 (단일/다중 상품)
- **결제 관리**: 결제 성공/실패 처리, 환불 요청 및 처리

### 👤 회원 관리
- **회원가입/로그인**: 이메일 기반 회원 인증
- **마이페이지**: 개인정보 조회 및 수정
- **회원 탈퇴**: 계정 삭제 기능
- **자동 로그인**: 쿠키 기반 로그인 유지

### 📝 리뷰 시스템
- **상품 리뷰**: 구매 후 상품 리뷰 작성
- **리뷰 추천**: 다른 사용자의 리뷰 추천 기능
- **리뷰 관리**: 리뷰 조회 및 관리

### ❓ 문의 시스템
- **문의 작성**: 상품 및 서비스 문의
- **문의 목록**: 사용자별 문의 내역 조회
- **문의 상세**: 문의 내용 상세 보기

### 🔧 관리자 기능
- **회원 관리**: 전체 회원 목록 및 정보 수정
- **상품 관리**: 상품 등록, 수정, 삭제
- **문의 관리**: 사용자 문의 조회 및 처리
- **결제 관리**: 결제 내역 조회 및 환불 처리
- **카테고리 관리**: 상품 카테고리 및 필드 순서 관리

## 🏗️ 프로젝트 구조

```
ssa/
├── build.gradle                    # Gradle 빌드 설정
├── src/
│   ├── main/
│   │   ├── java/web/ssa/
│   │   │   ├── cache/              # 캐시 관련 클래스
│   │   │   │   ├── CategoriesCache.java
│   │   │   │   └── ProductImgCache.java
│   │   │   ├── controller/         # 컨트롤러
│   │   │   │   ├── admin/          # 관리자 컨트롤러
│   │   │   │   ├── client/         # 클라이언트 컨트롤러
│   │   │   │   └── ...
│   │   │   ├── dto/                # 데이터 전송 객체
│   │   │   ├── entity/             # JPA 엔티티
│   │   │   ├── repository/         # 데이터 접근 계층
│   │   │   ├── service/            # 비즈니스 로직
│   │   │   └── util/               # 유틸리티 클래스
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── static/
│   │   └── webapp/
│   │       ├── css/
│   │       │   └── style.css       # 기본 스타일
│   │       ├── resources/
│   │       │   └── Ssa-Front/      # 프론트엔드 리소스
│   │       │       ├── css/
│   │       │       │   ├── admin.css      # 관리자 페이지 스타일
│   │       │       │   ├── auth.css       # 인증 페이지 스타일
│   │       │       │   ├── common.css     # 공통 스타일 (신규)
│   │       │       │   ├── profile.css    # 프로필 페이지 스타일
│   │       │       │   └── ...
│   │       │       ├── js/
│   │       │       └── assets/
│   │       └── WEB-INF/views/      # JSP 뷰 파일
│   │           ├── admin/          # 관리자 페이지
│   │           ├── client/         # 클라이언트 페이지
│   │           ├── shop/           # 쇼핑몰 페이지
│   │           └── ...
│   └── test/                       # 테스트 코드
```

## 🛠️ 기술 스택

### Backend
- **Spring Boot 2.7.x**: 웹 애플리케이션 프레임워크
- **Spring Data JPA**: 데이터 접근 계층
- **H2 Database**: 인메모리 데이터베이스
- **Gradle**: 빌드 도구
- **Thymeleaf**: 템플릿 엔진 (JSP와 혼용)

### Frontend
- **JSP**: 서버 사이드 렌더링
- **CSS3**: 스타일링
- **JavaScript**: 클라이언트 사이드 로직
- **Material Icons**: 아이콘 라이브러리

### External APIs
- **KakaoPay API**: 결제 시스템 연동

## 🚀 빌드 및 실행

### 1. 프로젝트 클론
```bash
git clone [repository-url]
cd ssa
```

### 2. 빌드
```bash
./gradlew build
```

### 3. 실행
```bash
./gradlew bootRun
```

### 4. 테스트 (선택사항)
```bash
./gradlew test
```

### 5. 테스트 제외하고 빌드
```bash
./gradlew build -x test
```

## 🌐 주요 페이지

### 클라이언트 페이지
- **메인 페이지**: `/index` - 쇼핑몰 메인 화면
- **상품 목록**: `/shop/productList` - 카테고리별 상품 목록
- **상품 상세**: `/shop/productDetail` - 상품 상세 정보
- **로그인**: `/login` - 사용자 로그인
- **회원가입**: `/register` - 신규 회원 가입
- **마이페이지**: `/mypage` - 개인정보 관리

### 관리자 페이지
- **관리자 메인**: `/admin` - 관리자 대시보드
- **회원 관리**: `/admin/users` - 전체 회원 관리
- **상품 관리**: `/admin/products` - 상품 등록/수정
- **문의 관리**: `/admin/inquiries` - 문의 내역 관리
- **결제 관리**: `/admin/payments` - 결제 내역 및 환불 처리
- **카테고리 관리**: `/admin/categories` - 카테고리 및 필드 순서 관리

## 💳 결제 시스템

### 결제 프로세스
1. **상품 선택**: 장바구니에서 상품 및 수량 선택
2. **결제 요청**: 카카오페이 결제 준비 요청
3. **결제 승인**: 사용자 결제 승인 처리
4. **결과 처리**: 성공/실패에 따른 페이지 이동

### 환불 프로세스
1. **환불 요청**: 사용자가 결제 취소 요청
2. **관리자 승인**: 관리자가 환불 승인
3. **환불 처리**: 카카오페이를 통한 실제 환불 처리

## 🎨 CSS 구조 개선

### 공통 CSS 파일 생성
- **`common.css`**: 중복되는 스타일을 통합한 공통 CSS 파일
- **버튼 스타일**: `.btn`, `.btn-primary`, `.btn-secondary`, `.btn-danger`
- **카드 스타일**: `.card` - 일관된 카드 디자인
- **폼 스타일**: `.form-group`, `.form-group input` 등
- **테이블 스타일**: `.data-table` - 통일된 테이블 디자인
- **유틸리티 클래스**: `.text-center`, `.mb-20`, `.mt-30` 등

### 중복 제거 효과
- **코드 중복 감소**: 동일한 스타일 정의 제거
- **유지보수성 향상**: 스타일 변경 시 한 곳에서만 수정
- **일관성 확보**: 모든 페이지에서 동일한 디자인 시스템 적용
- **성능 개선**: CSS 파일 크기 최적화

## 📝 주요 개선사항

### 1. 캐시 시스템 구현
- **ProductImgCache**: 상품 이미지 URL 캐싱으로 성능 향상
- **CategoriesCache**: 카테고리 정보 캐싱

### 2. 결제 로직 개선
- **SelectedProductDTO**: 결제 데이터 일관성 확보
- **에러 처리**: 결제 실패 시 적절한 에러 메시지 표시

### 3. 관리자 기능 확장
- **카테고리 관리**: 카테고리 및 필드 순서 관리 기능
- **사용자 관리**: 회원 정보 수정 및 관리 기능

### 4. CSS 구조 최적화
- **공통 스타일 분리**: 중복 코드 제거 및 재사용성 향상
- **반응형 디자인**: 모바일 환경 지원

## 🔧 설정 파일

### application.properties
```properties
# 데이터베이스 설정
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver

# JPA 설정
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 콘솔 활성화
spring.h2.console.enabled=true

# 파일 업로드 설정
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## 📞 문의 및 지원

프로젝트 관련 문의사항이나 버그 리포트는 이슈 트래커를 통해 제출해주세요.

---

**싸싸(SSA)** - 안전하고 편리한 온라인 쇼핑몰 🛒 