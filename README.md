 💻 방문 예약/조회 프로그램
-------------

방문객들이 쉽게 공공시설 방문 가능 여부 정보를 열람하여 현재 진행 중인 이벤트, 
입장인원정보를 확인하고 방문 여부 및 예약을 결정할 수 있도록 도와준다.
장소의 정보는 각 장소 관리자에 의해 직접 관리된다.

---

## 목차
1. [개발 환경](#✏️-개발 환경)
2. [기술 세부 스택](#✅-기술-세부-스택)
3. [개발 현황](#🗓️-개발-현황)
4. [추가 개발 예정](#🗓️-추가-개발-예정)
5. [View Page](#🖥️-View)

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



## 🗓️ 개발 현황 

[(Commit 이력 확인)](https://github.com/sussa3007/line-up-project/commits/main)

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
    - OAuth2 적용 - 구글, 카카오 로그인.
        - 소셜 로그인 회원의 부족한 정보 등록하기 위해 가입후 정보 수정 페이지로 리다이렉트.
    - Auth 기능 개발 완료, 기능 추가 사항
        - Super Admin 모든 사용자 정보 조회시 사용자의 상태, 가입 경로 확인 기능 추가.
        - Super Admin의 사용자 정보 수정시 비밀번호 Pass 하도록 하였고, 사용자의 상태와 권한 변경 기능 추가.
- Request 기능 추가
    - 사용자의 요청 / 문의 사항 접수 기능 추가
    - 사용자의 관리자 권한, 회원 탈퇴, 클레임, 문의사항 접수 가능하도록 별도 Ticket 페이지 만들어 구현.
    - 기존 회원 정보만 볼수 있었던 마이 페이지에서 사용자가 접수한 요청사항을 리스트와 상세 내역을 볼수 있도록 구현.
    - Super Admin은 전체 사용자의 요청을 확인할 수 있으며, 요청에 대한 관리자 메세지도 보낼수 있도록 구현.
  

## 🗓️ 추가 개발 예정

- 조회시 페이징 기능 구현 예정.
    - 다수 정보 조회시 페이지로 리턴하도록 구현되어 있음 View와 Request만 수정하면 됨.
- 회원 가입과 수정시 Email 알림 서비스 기능 추가 구현 예정.
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

![회원 정보수정](https://user-images.githubusercontent.com/110886399/205122273-89cc8f8f-438e-4bb3-ae31-81562bc10aa8.png)


- 일반 / 비회원 이벤트 리스트 조회 페이지

![이벤트리스트](https://user-images.githubusercontent.com/110886399/204592924-c1c52b2f-62f8-4e23-8dfd-3113cf19e8d3.png)


- 일반 / 비회원 장소 리스트 조회 페이지

![장소리스트](https://user-images.githubusercontent.com/110886399/204592996-64897f00-db63-469e-891f-cb866d385d88.png)


- 일반 회원 / 관리자 문의사항 접수 페이지

![문의사항](https://user-images.githubusercontent.com/110886399/205122389-a9ac0b8f-ce08-4374-98da-cdbd1ac15cda.png)

- 일반 회원 / 관리자 문의사항 상세 페이지

![문의사항 상세](https://user-images.githubusercontent.com/110886399/205122521-d60dd85e-1f6b-415a-ab2f-732b0907ffb4.png)


- 관리자 장소 리스트 조회 페이지

![관리자 장소](https://user-images.githubusercontent.com/110886399/205122593-3f90e107-e1bd-4504-b000-2fad63c4b1c5.png)

- 관리자 이벤트 리스트 조회 페이지

![관리자 이벤트](https://user-images.githubusercontent.com/110886399/205122654-9fed9a37-8b06-484c-bb69-5a1863827fc8.png)


- 관리자 새 장소 등록 페이지

![관리자 장소등록](https://user-images.githubusercontent.com/110886399/205122745-75542784-0cbb-4d9e-9557-ef5ff7ec6eb9.png)


- 관리자 새 이벤트 등록 페이지

![관리자 이벤트 등록](https://user-images.githubusercontent.com/110886399/205122803-471e59fa-b6e4-4710-af3e-1b190aea9c89.png)


- 관리자 장소 수정 페이지

![관리자 장소 수정](https://user-images.githubusercontent.com/110886399/205122984-3972e6fb-f42a-47bd-961a-28da0ce15e8e.png)



- 관리자 이벤트 수정 페이지

![관리자 이벤트 수정](https://user-images.githubusercontent.com/110886399/205122895-4b141345-b9c3-4626-acbd-cb6d9f7acfc4.png)


- Super 관리자 전체 장소 조회 페이지

![슈퍼어드민 장소조회](https://user-images.githubusercontent.com/110886399/205123170-4f741af1-ef7f-45b2-abbd-b220a76df332.png)

- Super 관리자 전체 이벤트 조회 페이지

![슈퍼어드민 이벤트조회](https://user-images.githubusercontent.com/110886399/205123255-e2792714-8a22-46ae-bdc1-8a21cb3d4bb5.png)

- Super 관리자 전체 회원 조회 페이지

![슈퍼어드민 회원 조회](https://user-images.githubusercontent.com/110886399/205123346-f86c7e7f-8e41-404f-9cd1-13fa3afc7abd.png)

- Super 관리자 회원 수정 페이지

![슈퍼어드민 회원 수정](https://user-images.githubusercontent.com/110886399/205123473-8e285993-1d28-4bb1-b75c-f3978e956b21.png)

- Super 관리자 전체 요청 조회 페이지

![슈퍼어드민 요청 조회](https://user-images.githubusercontent.com/110886399/205123555-2a5c6e8a-d990-4c52-9364-e1d3416d75b2.png)

- Super 관리자 요청 처리 페이지

![슈퍼어드민 요청 처리](https://user-images.githubusercontent.com/110886399/205123633-2d6007e2-a1b4-4db6-8d98-cac56ed134cb.png)

- Error View

![에러 알림](https://user-images.githubusercontent.com/110886399/204716965-d3a7eb02-a114-43c3-b490-b382d659fc3a.png)

- Alert View

![알림](https://user-images.githubusercontent.com/110886399/204717088-b7646839-c3c8-45aa-9304-7bd2376bab69.png)



