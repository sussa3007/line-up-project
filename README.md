방문 예약 프로그램
-------------

방문객들이 쉽게 공공시설 방문 가능 여부 정보를 열람하여 현재 진행 중인 이벤트, 
입장인원정보를 확인하고 방문 여부 및 예약을 결정할 수 있도록 도와준다.
장소의 정보는 각 장소 관리자에 의해 직접 관리된다.


## 개발 환경

* Intellij IDEA Ultimate 2022.2.
* Java 11 -> 16
* Gradle 7.5
* Spring Boot 2.7.4

## 기술 세부 스택

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



## 개발 현황

- View : 모든 페이지 CSS 적용, Fragments 추가 하여 상단 Nav Bar 적용.(어드민, 일반 회원 권한에 따른 View 적용)
    - Error 페이지, Error 경고 알림 등 Error 처리 추가.
    - 데이터 수정 요청시 Confirm 페이지 만들어 자동 이전 페이지 이동 구현.
- Auth : Srping Security + JWT 구현. 토큰 관리는 Cookie하고 있으며, Access 토큰 만료시 Refresh 토큰 조회하여 자동 토큰 재발급 되도록 구현.
    - 기존 어드민 한명만 관리하도록 설계하였지만 여러명의 어드민이 관리할 수 있도록 개발.
    - 일반 회원, 비 회원 모두 서비스를 이용할 수 있도록 회원 가입 구현.
    - 다수 어드민 추가되며 변경 사항
        - 어드민 별 장소와 이벤트 조회 기능 추가.
        - 어드민 가입 기능 추가.
        - 어드민 및 일반 회원 마이페이지 추가하여 회원 정보 수정 기능 추가.
        - 어드민 페이지의 View CSS 적용.


## 추가 개발 예정

- OAuth2 적용하여 Google, Kakao 로그인 구현 예정.
- Super Admin Role 추가하여 모든 기능 관리 가능하도록 구현 예정.
- 일반 회원의 이벤트 방문 예약 기능 구현 예정.(엔티티와 서비스 로직 수정사항이 많아 별도 브런치 개발 예정)
    - 일반 회원의 마이 페이지에 방문 예약한 이벤트 리스트 확인 및 취소 기능 구현 예정. 
- 개발 예정 기능 구현 후 테스트 코드 수정. 현재 테스트 73/108 Pass.
    - 테스트 실패로 GitHub Actions Failed


## View

- 로그인 페이지

![로그인 페이](https://user-images.githubusercontent.com/110886399/204501366-b65d4175-9154-4d8e-a0ab-14e250aa86b2.png)

- 회원 가입 페이지

![회원 가입 페이](https://user-images.githubusercontent.com/110886399/204501460-6818e17d-9c02-462c-aa63-22425a8b5740.png)


- 회원 정보 수정 페이지

![회원 정보 수정 페이](https://user-images.githubusercontent.com/110886399/204501274-905e4d6d-5989-4065-9338-370c7e43b407.png)

- 일반 / 비회원 이벤트 리스트 조회 페이지

![이벤트 리스트](https://user-images.githubusercontent.com/110886399/204500632-fc38fcd6-9ff6-4cd3-8294-4d033b988a1f.png)

- 일반 / 비회원 장소 리스트 조회 페이지

![장소 목록](https://user-images.githubusercontent.com/110886399/204500758-406e0810-7b7d-4cfc-ab15-981d52ac7dc5.png)

- 관리자 장소 리스트 조회 페이지

![관리자 장소 목록](https://user-images.githubusercontent.com/110886399/204500848-fc854475-bcd0-4689-ba1f-07d998d08e4d.png)

- 관리자 이벤트 리스트 조회 페이지

![관리자 이벤트 목](https://user-images.githubusercontent.com/110886399/204500928-07b51515-636c-4fac-9fc1-2aaea3949407.png)

- 관리자 장소 수정 페이지


![관리자 장소 수정페이지](https://user-images.githubusercontent.com/110886399/204501034-cfcff478-8211-44ef-b7f3-094694953752.png)


- 관리자 이벤트 수정 페이지

![관리자 이벤트 수정페이](https://user-images.githubusercontent.com/110886399/204501115-7500d73e-1abe-4b10-ac54-a184beb68c40.png)