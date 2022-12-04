 💻 방문 예약/조회 프로그램(줄서자 어플리케이션)
-------------

방문객들이 쉽게 공공시설 방문 가능 여부 정보를 열람하여 현재 진행 중인 이벤트, 
입장인원정보를 확인하고 방문 여부 및 예약을 결정할 수 있도록 도와준다.
장소의 정보는 각 장소 관리자에 의해 직접 관리된다.

---

## 목차


1. [개발 환경](#개발-환경)
2. [기술 세부 스택](#기술-세부-스택)
3. [개발 현황](#개발-현황)
4. [추가 개발 예정](#추가-개발-예정)
5. [View Page](#View)

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

[(Commit 이력 확인)](https://github.com/sussa3007/line-up-project/commits/main)

- 핵심 기능 개발 완료.
- View : 모든 페이지 CSS 적용, Fragments 추가 하여 상단 Nav Bar 적용.(슈퍼 어드민, 어드민, 일반 회원 권한에 따른 View 적용)
    - Error 페이지, Error 경고, 알림 등 Error 처리 추가.
    - 데이터 수정 요청시 Confirm 페이지 만들어 자동 이전 페이지 이동 구현.
    - 슈퍼 관리자가 사용자의 정보 수정시에는 상단 브라우저 알림창 표시 하도록 추가.
    - 장소 등록 및 조회 시 관리자의 이메일 정보를 표시하도록 수정. 
    - 모든 페이지 페이지네이션 구현 완료.
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
- 검색 기능 추가
    - 각 페이지별 검색요소 모두 적용하여 다중 검색으로 검색할 수 있도록 구현.
    - 다중 검색은 QueryDSL 동적 쿼리 작성하여 구현.
  

## 추가 개발 예정

- 공지사항 기능 추가 예정.
- 회원 가입과 수정시 Email 알림 서비스 기능 추가 구현 예정.
- 일반 회원의 이벤트 방문 예약 기능 구현 예정.(엔티티와 서비스 로직 수정사항이 많아 별도 브런치 개발 예정)
    - 일반 회원의 마이 페이지에 방문 예약한 이벤트 리스트 확인 및 취소 기능 구현 예정. 
- 방문지 리뷰 게시판 구현 예정.



## Refactoring


- 운영중에는 발생하지 않겠지만, 서버에 사용자 정보 없고 토큰 정보는 있을때(DB의 Email 정보 없을때), Access Denied 에러 브라우저 알림창 루프 발생, Redirect 수정 필요.
- 개별 관리자의 전체 이벤트 조회시 1+N (Admin Entity 조회시, fetch size로 해결안됨, QueryDSL 사용) 문제 발생.
- 개발 예정 기능 구현 후 테스트 코드 수정. 현재 테스트 73/108 Pass.
    - 테스트 실패로 GitHub Actions Failed
- 에러와 예외, 브라우저 알림창 호출시 중복 코드 리팩토링 필요.
- 회원 가입 및 정보 수정시 View에서 Email Validation 기능 추가 필요.
    - 타임리프 패턴 검증 적용 필요.


## View

- 로그인 페이지

![image](https://user-images.githubusercontent.com/110886399/205495756-4318ac89-d2a1-48ab-9059-75d551468c70.png)


- 회원 가입 페이지


![image](https://user-images.githubusercontent.com/110886399/205495775-a2c1f0e3-5e39-4a7b-bdb7-bdddf723a9e8.png)


- 회원 정보 수정 페이지


![image](https://user-images.githubusercontent.com/110886399/205495810-eaab8e73-1477-49f5-ab03-d8d8dbe55e1a.png)


- 일반 / 비회원 이벤트 리스트 조회 페이지

![image](https://user-images.githubusercontent.com/110886399/205495833-2bdf1dab-0611-4f0a-9d8e-47d92de17baf.png)

- 일반 / 비회원 장소 리스트 조회 페이지


![image](https://user-images.githubusercontent.com/110886399/205495859-76215979-e64f-403f-a325-7713884e8f1e.png)



- 일반 회원 / 관리자 문의사항 접수 페이지


![image](https://user-images.githubusercontent.com/110886399/205495885-da01c2bd-d67b-4538-b1e1-d91aebb2be64.png)


- 일반 회원 / 관리자 문의사항 상세 페이지

![image](https://user-images.githubusercontent.com/110886399/205495931-dea06e54-7f55-499d-a8c4-933d9ca7ca0d.png)


- 관리자 장소 리스트 조회 페이지




![image](https://user-images.githubusercontent.com/110886399/205496197-f7e6dabd-a8bf-417e-8dcb-ba5f479f8992.png)



- 관리자 이벤트 리스트 조회 페이지



![image](https://user-images.githubusercontent.com/110886399/205496219-84908f1f-f8a9-421a-875b-6275eddb470a.png)



- 관리자 새 장소 등록 페이지


![image](https://user-images.githubusercontent.com/110886399/205496264-3f0f3229-cf04-4ef7-bf59-f9b36b1c04bd.png)



- 관리자 새 이벤트 등록 페이지


![image](https://user-images.githubusercontent.com/110886399/205496309-4cf85902-2ffd-4645-8e7d-8c3970e599c7.png)



- 관리자 장소 수정 페이지


![image](https://user-images.githubusercontent.com/110886399/205496337-d108bc71-8ccf-4528-9d2e-cf08ff52593a.png)




- 관리자 이벤트 수정 페이지


![image](https://user-images.githubusercontent.com/110886399/205496371-fbdd32dd-30b7-4a2e-81eb-00d6f3cc5999.png)


- Super 관리자 전체 장소 조회 페이지


![image](https://user-images.githubusercontent.com/110886399/205496413-0d397ce9-04b5-420d-9f37-d50ebcc301e3.png)


- Super 관리자 전체 이벤트 조회 페이지


![image](https://user-images.githubusercontent.com/110886399/205496424-0adc333d-30f6-4a20-a602-d8f9d66f38e6.png)



- Super 관리자 전체 회원 조회 페이지


![image](https://user-images.githubusercontent.com/110886399/205496452-618aca86-9443-465c-8626-a28c9cfeabb7.png)


- Super 관리자 회원 수정 페이지


![image](https://user-images.githubusercontent.com/110886399/205496486-8da2f2eb-ca0c-46e6-87fa-2f1454a88893.png)


- Super 관리자 전체 요청 조회 페이지


![image](https://user-images.githubusercontent.com/110886399/205496531-d46906b9-1494-4a97-93ee-1c584c990e58.png)


- Super 관리자 요청 처리 페이지


![image](https://user-images.githubusercontent.com/110886399/205496554-0941224f-b912-4122-86c9-d114b673bb6d.png)


- Error View

![에러 알림](https://user-images.githubusercontent.com/110886399/204716965-d3a7eb02-a114-43c3-b490-b382d659fc3a.png)

- Alert View

![알림](https://user-images.githubusercontent.com/110886399/204717088-b7646839-c3c8-45aa-9304-7bd2376bab69.png)



