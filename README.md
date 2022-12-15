 💻 방문 예약/조회 프로그램(줄서자 어플리케이션)
-------------

방문객들이 쉽게 공공시설 방문 가능 여부 정보를 열람하여 현재 진행 중인 이벤트, 
입장인원정보를 확인하고 방문 여부 및 예약을 결정할 수 있도록 도와준다.
장소의 정보는 각 장소 관리자에 의해 직접 관리된다.

Java/Spring 강의 수강후 기본 기능만 있는 어플리케이션 설계를 바탕으로,
처음부터 다시 개발하여 현재까지 학습한 내용을 모두 적용하며 추가 기능 개발중. 

---

[✅ 배포 페이지 https://zulseoza.site/](https://zulseoza.site/)

- 장소 관리자 계정
    - admin@gmail.com / 1111!
- 사용자 계정
    - test@test.com / 1111!
- 슈퍼 어드민 계정
    - super@test.com / 1111!



## 목차


1. [개발 환경](#개발-환경)
2. [기술 세부 스택](#기술-세부-스택)
3. [개발 현황](#개발-현황)
4. [추가 개발 예정](#추가-개발-예정)
5. [View Page](#View)

## 개발 환경

* Intellij IDEA Ultimate 2022.2.
* Java 11 -> 16 -> *17*
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

배포

* GitHub Actions AWS
* AWS EC2, S3, RDS
* Nginx

그 외

* QueryDSL
* Tailwind CSS



## 개발 현황 

[(Commit 이력 확인)](https://github.com/sussa3007/line-up-project/commits/main)

- 핵심 기능 개발 완료.[~ 22/11/10]
- Auth : Srping Security + JWT 구현.[~ 22/11/31]
    - 다수 어드민 추가되며 변경 사항
        - 어드민 별 장소와 이벤트 조회 기능 추가.
        - 어드민 가입 기능 추가.
        - 어드민 및 일반 회원 마이페이지 추가하여 회원 정보 수정 기능 추가.
    - Super Admin Role 추가 (Commit 확인)
        - 모든 관리자들의 장소, 이벤트 정보 조회, 수정 기능 구현.
        - 모든 사용자(관리자 포함) 정보 조회, 수정 기능 구현.
    - OAuth2 적용 - 구글, 카카오 로그인.
        - 소셜 로그인 회원의 부족한 정보 등록하기 위해 가입후 정보 수정 페이지로 리다이렉트.
    - Auth 기능 개발 완료, 기능 추가 사항
        - Super Admin 모든 사용자 정보 조회시 사용자의 상태, 가입 경로 확인 기능 추가.
        - Super Admin의 사용자 정보 수정시 비밀번호 Pass 가능 하도록 수정 하였고, 사용자의 상태와 권한 변경 기능 추가.
- Request 기능 추가[~ 22/12/01]
    - 사용자의 요청 / 문의 사항 접수 기능 추가
    - 사용자의 관리자 권한, 회원 탈퇴, 클레임, 문의사항 접수 가능하도록 별도 Ticket 페이지 만들어 구현.
    - 기존 회원 정보만 볼수 있었던 마이 페이지에서 사용자가 접수한 요청사항을 리스트와 상세 내역을 볼수 있도록 구현.
    - Super Admin은 전체 사용자의 요청을 확인할 수 있으며, 요청에 대한 관리자 메세지도 보낼수 있도록 구현.
- 검색 기능 추가[~ 22/12/04]
    - 각 페이지별 검색요소 모두 적용하여 다중 검색으로 검색할 수 있도록 구현.
    - 다중 검색은 QueryDSL 동적 쿼리 작성하여 구현.
- GA EC2 배포 완료[~ 22/12/07]
- 공지 사항 기능 추가[~ 22/12/09]
    - 마크다운 에디터 기능 추가.
    - 쓰기 모드와 미리보기 모드를 적용하여 현재 작성한 마크다운 글이 정상적으로 작성 되었는지 확인 가능
    - 공지사항 및 모든 게시글은 Markdown 문법으로 작성한다.
    - 마크다운 -> html 변환 라이브러리 추가 하여 변환된 마크다운 Text로 DB 저장.
    - 전체 공지사항과 장소별 공지사항 별도로 관리 가능.
- SuperAdmin 사용자와 장소 관리자에게 메세지 전송 기능 추가.[~ 22/12/10]
- 회원 가입시 이메일 중복 확인 기능 추가.[~ 22/12/13]
  

## 추가 개발 예정

- 일반 회원의 이벤트 방문 예약 기능 구현 예정.
    - 일반 회원의 마이 페이지에 방문 예약한 이벤트 리스트 확인 및 취소 기능 구현 예정.
- 사용자의 방문지 리뷰 작성 게시판 구현 예정.
- 회원 가입과 정보 수정시 Email 알림 서비스 기능 추가 구현 예정.



## Refactoring


- 운영중에는 발생하지 않겠지만, 서버에 사용자 정보 없고 토큰 정보는 있을때(DB의 Email 정보 없을때), Access Denied 에러 브라우저 알림창 루프 발생, Redirect 수정 필요.
- 개별 관리자의 전체 이벤트 조회시 1+N (Admin Entity 조회시, fetch size로 해결안됨, QueryDSL 사용) 문제 발생.
- 개발 예정 기능 구현 후 테스트 코드 수정. 현재 테스트 73/108 Pass.
    - 테스트 실패로 GitHub Actions Failed
    - 임시로 배포위해 테스트 코드 별도로 수정 중


## View

 </br>
 <details>
   <summary> 로그인/회원가입 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/207334427-e2b35413-f5d0-4874-aab9-575582d239bb.png)

![image](https://user-images.githubusercontent.com/110886399/207341096-6faaa08f-d651-43f2-8ebc-8954841a66b7.png)


![image](https://user-images.githubusercontent.com/110886399/207327832-343b9fce-c0a6-4fa7-ba40-af28fc7a5a2c.png)


![image](https://user-images.githubusercontent.com/110886399/207341431-382b9815-6075-4c56-a8e0-573905bb8838.png)


 </details>

 </br>
 <details>
   <summary> 회원 정보 수정 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/206916403-b1059bd2-551c-4924-bf0a-3236c9db2327.png)


 </details>

 </br>
 <details>
   <summary> 일반 / 비회원 이벤트,장소 리스트 조회 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/206916460-24a2992b-f751-4687-93ad-687dc02dbf1e.png)


![image](https://user-images.githubusercontent.com/110886399/206916479-7f3343ad-ac8a-4cc2-98ee-66c5ae8e6269.png)



 </details>

 </br>
 <details>
   <summary> 전체 / 장소 공지사항 조회 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/207236953-f54613fc-3729-4cc1-9556-9cc8e49d23f1.png)

![image](https://user-images.githubusercontent.com/110886399/207236994-7088f1bb-6307-4489-9e87-62cc4d2b0281.png)


 </details>



 </br>
 <details>
   <summary> 일반 / 비회원 이벤트,장소 상세 조회 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/207237055-e1bd98f4-ffb3-42e6-b11b-e3d05eac596d.png)


![image](https://user-images.githubusercontent.com/110886399/207237104-aad4dc33-245b-42b7-9147-5969a8f0341b.png)



 </details>


 </br>
 <details>
   <summary> 일반 회원/장소 관리자 메세지 조회 페이지 </summary>



![image](https://user-images.githubusercontent.com/110886399/207237157-64d62152-8572-48e9-88c8-fbb2b051319c.png)




 </details>


 </br>
 <details>
   <summary> Super Admin / 장소 관리자 관리 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/206916527-02039b02-f95d-42d8-8964-2e1e77d57877.png)

![image](https://user-images.githubusercontent.com/110886399/206916554-a6467f2d-89d3-4164-b710-4f60e766e0dc.png)



 </details>



 </br>
 <details>
   <summary> 일반 회원 / 관리자 문의사항 접수,상세 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/206916501-4e0703f1-12b3-4a9e-9956-89b53483f9fc.png)


![image](https://user-images.githubusercontent.com/110886399/206916515-050fb298-7d23-46fa-9828-1c47511cd93f.png)


 </details>


 </br>
 <details>
   <summary> 관리자 장소,이벤트 리스트 조회 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/206916595-2b9ffb89-00be-459e-9a21-36adcb083ddc.png)

![image](https://user-images.githubusercontent.com/110886399/206916645-d5ac6e13-e239-43ff-8368-11b6066bdffa.png)


 </details>

 </br>
 <details>
   <summary> 관리자 새 장소, 이벤트 등록 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/206916664-1377d9cc-52bd-4480-8ef2-4d9117e9a8df.png)

![image](https://user-images.githubusercontent.com/110886399/206916685-215bd497-213e-47f7-8bda-05c1a887ec30.png)


 </details>


 </br>
 <details>
   <summary> 관리자 장소, 이벤트 수정 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/206916711-b1df7049-0d4a-47f4-a792-e27e5736f54c.png)


![image](https://user-images.githubusercontent.com/110886399/206916728-b0182124-48f3-45e2-92da-f5e9306c8d49.png)

 </details>

 </br>
 <details>
   <summary> Super 관리자 전체 장소, 이벤트 조회 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/206916825-b1aede38-1951-43fa-8baf-354792713fab.png)


![image](https://user-images.githubusercontent.com/110886399/206916837-2f8ccaf3-a079-4f47-b424-48c24df169c3.png)


 </details>

 </br>
 <details>
   <summary> Super 관리자 전체 회원 조회/수정 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/207237454-37098e3f-d697-4afa-822f-c8e888f1ccb8.png)


![image](https://user-images.githubusercontent.com/110886399/206916943-4f2b1897-3f52-4d6a-8511-5c269cea2698.png)


 </details>


 </br>
 <details>
   <summary> Super 관리자 전체 요청 조회/처리 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/206916978-381c1cd2-9589-4194-b689-8a6b5cd6c12a.png)

![image](https://user-images.githubusercontent.com/110886399/206917018-03b0feab-3d00-4a8d-aeae-7aebb4b2cd84.png)


 </details>

 </br>
 <details>
   <summary> 공지사항 / 장소 공지사항 관리 페이지 </summary>

![image](https://user-images.githubusercontent.com/110886399/206917050-847f3189-3684-4736-b39a-8669ff0ced2d.png)

![image](https://user-images.githubusercontent.com/110886399/206917076-61e1b106-cf44-42e5-bf0a-af36e6507503.png)



 </details>



 </br>
 <details>
   <summary> 공지사항 / 장소 공지사항 등록, 수정 페이지 </summary>

- 쓰기 모드와 미리보기 모드를 적용하여 현재 작성한 마크다운 글이 정상적으로 작성 되었는지 확인 가능


![image](https://user-images.githubusercontent.com/110886399/206917135-2d300fd9-1ca8-4b7d-9ccc-6286e4b559aa.png)



![image](https://user-images.githubusercontent.com/110886399/206917160-c39a18e3-f37b-4529-8743-ceea065486c0.png)


![image](https://user-images.githubusercontent.com/110886399/206917250-63abbc99-3e64-42ce-a334-b1286c247dd0.png)


![image](https://user-images.githubusercontent.com/110886399/206917273-a2687ae1-017f-4443-af53-068a3591e718.png)



 </details>

 </br>
 <details>
   <summary> Super Admin 메세지 관리 페이지 </summary>


![image](https://user-images.githubusercontent.com/110886399/207237542-fb7bd162-386b-43e2-8644-1529b54a0da6.png)



 </details>




 </br>
 <details>
   <summary> Error / Alert View </summary>


![에러 알림](https://user-images.githubusercontent.com/110886399/204716965-d3a7eb02-a114-43c3-b490-b382d659fc3a.png)


![알림](https://user-images.githubusercontent.com/110886399/204717088-b7646839-c3c8-45aa-9304-7bd2376bab69.png)


![image](https://user-images.githubusercontent.com/110886399/206917293-91e514b7-9308-4b25-a255-c3768de7daf2.png)


 </details>




