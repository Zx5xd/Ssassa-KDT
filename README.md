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