 💻 방문 예약/조회 프로그램
-------------

방문객들이 쉽게 공공시설 방문 가능 여부 정보를 열람하여 현재 진행 중인 이벤트, 
입장인원정보를 확인하고 방문 여부 및 예약을 결정할 수 있도록 도와준다.
장소의 정보는 각 장소 관리자에 의해 직접 관리된다.


## ✏️ 개발 환경

* Intellij IDEA Ultimate 2022.2.
* Java 11 -> 16
* Gradle 7.5
* Spring Boot 2.7.4

## ✅ 기술 세부 스택

Spring Boot

* Spring Boot Actuator
* Spring Data JPA
* Spring Web
* Thymeleaf
* Spring Security + JWT + OAuth2
* H2 Database (Local)
* MySQL Database (Server)
* Lombok

버전 관리 Tool

* GitHub
* GitKraken

그 외

* QueryDSL
* Tailwind CSS



## 🗓️ 개발 현황 [(Commit 이력 확인)](https://github.com/sussa3007/line-up-project/commits/main)

- 핵심 기능 개발 완료.
- View : 모든 페이지 CSS 적용, Fragments 추가 하여 상단 Nav Bar 적용.(슈퍼 어드민, 어드민, 일반 회원 권한에 따른 View 적용)
    - Error 페이지, Error 경고, 알림 등 Error 처리 추가.
    - 데이터 수정 요청시 Confirm 페이지 만들어 자동 이전 페이지 이동 구현.
    - 슈퍼 관리자가 사용자의 정보 수정시에는 상단 브라우저 알림창 표시 하도록 추가.
    - 장소 등록 및 조회 시 관리자의 이메일 정보를 표시하도록 수정. 
- Auth : Srping Security + JWT 구현. 토큰 관리는 Cookie하고 있으며, Access 토큰 만료시 Refresh 토큰 조회하여 자동 토큰 재발급 되도록 구현.
    - 기존 어드민 한명만 관리하도록 설계하였지만 여러명의 어드민이 관리할 수 있도록 개발.
    - 일반 회원, 비 회원 모두 서비스를 이용할 수 있도록 회원 가입 구현.
    - 다수 어드민 추가되며 변경 사항
        - 어드민 별 장소와 이벤트 조회 기능 추가.
        - 어드민 가입 기능 추가.
        - 어드민 및 일반 회원 마이페이지 추가하여 회원 정보 수정 기능 추가.
        - 어드민 페이지의 View CSS 적용.
    - Super Admin Role 추가 (Commit 확인)
        - 모든 관리자들의 장소, 이벤트 정보 조회, 수정 기능 구현.
        - 모든 사용자(관리자 포함) 정보 조회, 수정 기능 구현.
  


## 🗓️ 추가 개발 예정

- OAuth2 적용하여 Google, Kakao 로그인 구현 예정.
    - OAuth2 로그인시 서비스 이용하기 위한 부족한 정보 등록 및 수정 기능 추가 예정.
    - Auth 개발 완료되면, 회원 가입과 수정시 Email 알림 서비스 기능 추가 구현 예정.
- 조회시 페이징 기능 구현 예정.
    - 다수 정보 조회시 페이지로 리턴하도록 구현되어 있음 View와 Request만 수정하면 됨.
- 일반 회원의 이벤트 방문 예약 기능 구현 예정.(엔티티와 서비스 로직 수정사항이 많아 별도 브런치 개발 예정)
    - 일반 회원의 마이 페이지에 방문 예약한 이벤트 리스트 확인 및 취소 기능 구현 예정. 



## 🛠️ Refactoring


- 운영중에는 발생하지 않겠지만, 서버에 사용자 정보 없고 토큰 정보는 있을때(DB의 Email 정보 없을때), Access Denied 에러 브라우저 알림창 루프 발생, Redirect 수정 필요.
- 개별 관리자의 전체 이벤트 조회시 1+N (Admin Entity 조회시, fetch size로 해결안됨, QueryDSL 사용) 문제 발생.
- 개발 예정 기능 구현 후 테스트 코드 수정. 현재 테스트 73/108 Pass.
    - 테스트 실패로 GitHub Actions Failed
- 에러와 예외, 브라우저 알림창 호출시 중복 코드 리팩토링 필요.
- 회원 가입 및 정보 수정시 View에서 Email Validation 기능 추가 필요.
    - 타임리프 패턴 검증 적용 필요.


## 🖥️ View

- 로그인 페이지

![로그인](https://user-images.githubusercontent.com/110886399/204592365-ae52cccc-1d3c-48dc-a09b-8721f4df37cf.png)

- 회원 가입 페이지

![회원가입](https://user-images.githubusercontent.com/110886399/204592474-373ed057-dc03-4da0-97b6-626f7d74ab87.png)


- 회원 정보 수정 페이지

![회원 정보 수정](https://user-images.githubusercontent.com/110886399/204592632-bd00db3c-e351-490f-bd12-af706933919a.png)


- 일반 / 비회원 이벤트 리스트 조회 페이지

![image](https://user-images.githubusercontent.com/110886399/204592924-c1c52b2f-62f8-4e23-8dfd-3113cf19e8d3.png)


- 일반 / 비회원 장소 리스트 조회 페이지

![image](https://user-images.githubusercontent.com/110886399/204592996-64897f00-db63-469e-891f-cb866d385d88.png)


- 관리자 장소 리스트 조회 페이지

![관리자 장소 목록](https://user-images.githubusercontent.com/110886399/204500848-fc854475-bcd0-4689-ba1f-07d998d08e4d.png)

- 관리자 이벤트 리스트 조회 페이지

![관리자 이벤트 목](https://user-images.githubusercontent.com/110886399/204500928-07b51515-636c-4fac-9fc1-2aaea3949407.png)


- 관리자 새 장소 등록 페이지

![image](https://user-images.githubusercontent.com/110886399/204594768-b92fa9ed-a85d-4118-86be-760e38c7f92b.png)


- 관리자 새 이벤트 등록 페이지

![image](https://user-images.githubusercontent.com/110886399/204594910-8f722abe-9cf0-4f0d-953b-411b41975ef9.png)


- 관리자 장소 수정 페이지

![관리자 장소 수정](https://user-images.githubusercontent.com/110886399/204593196-099254fd-2244-4a7c-8a5e-77710f05445b.png)



- 관리자 이벤트 수정 페이지

![이벤트 수정](https://user-images.githubusercontent.com/110886399/204593347-5f1c714d-0106-4748-884c-85cafa329905.png)


- Super 관리자 전체 장소 조회 페이지

![슈퍼관리자 전체장소](https://user-images.githubusercontent.com/110886399/204593510-8f9b381d-1414-4604-9698-3655d331e3cb.png)

- Super 관리자 전체 이벤트 조회 페이지

![image](https://user-images.githubusercontent.com/110886399/204593633-fdb26e09-1ac8-4710-ae34-44f38cbc7817.png)

- Super 관리자 전체 회원 조회 페이지

![image](https://user-images.githubusercontent.com/110886399/204593721-63748aff-7fb7-4809-aba6-da3847625a7f.png)

- Super 관리자 회원 수정 페이지

![image](https://user-images.githubusercontent.com/110886399/204594176-d60ef0e6-88cf-420a-b0dc-7c4c3be8c5ac.png)




