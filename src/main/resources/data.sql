

insert into `place` (`place_type`, `place_name`, `address`, `phone_number`, `capacity`, `memo`)
values
    ('SPORTS', '스포츠 센터', '경기도 성남시 판교로 999', '010-9999-0000', 20, '스포츠 센터 예약 가능'),
    ('RESTAURANT', '스프링 식당', '서울시 강남구 가나대로 123', '010-1234-5678', 10, '테스트 메모'),
    ('SPORTS', '스프링 스키장', '하늘 천국 천국로 555', '010-1004-1004', 9000, null),
    ('COMMON', '스프링 컴퍼니', '111, Gana-ro, Gangnam-gu, Seoul', '010-1111-1111', 50, '회사 방문 가능'),
    ('PARTY', '스프링 파티룸', '서울시 강남구 가나대로 123 2층', '010-1234-5678', 10, '파티룸 예약 가능'),
    ('PARTY', '자바 파티룸', '부산시 중구 가나대로 123 2층', '010-1234-5671', 30, '파티룸 예약 가능'),
    ('COMMON', '자바 컴퍼니', '부산시 해운대 가나대로 123 2층', '010-1234-5672', 20, '가족같은 분위기'),
    ('SPORTS', '자바 스포츠 센터', '부산시 사상구 가나대로 123 2층', '010-1234-5673', 30, '스쿼시 ㄱㄱ'),
    ('COMMON', '코딩 컴퍼니', '서울시 동작구 가나대로 123 2층', '010-1234-5674', 20, '가족같은 회사'),
    ('RESTAURANT', '자바 식당', '서울시 강서구 가나대로 123 2층', '010-1234-5675', 10, '먹방ㄱㄱ'),
    ('PARTY', '코딩 파티룸', '부산시 강서구 가나대로 123 2층', '010-1234-5676', 50, '술파티 ㄱ'),
    ('SPORTS', '코딩 테니스장', '제주도 서귀포시 가나대로 123 2층', '010-1234-5677', 9000, '테니스 한겜')
;

insert into `event` (`place_id`, `event_name`, `event_status`, `event_start_datetime`, `event_end_datetime`, `current_number_of_people`, `capacity`, `memo`)
values
    (1, '운동1', 'OPENED', '2022-08-01 09:00:00', '2022-08-01 12:00:00', 0, 20, 'test memo1'),
    (1, '운동2', 'OPENED', '2022-08-01 13:00:00', '2022-08-01 12:00:00', 0, 20, 'test memo2'),
    (2, '행사1', 'OPENED', '2022-08-02 09:00:00', '2022-08-02 12:00:00', 0, 30, 'test memo3'),
    (2, '행사2', 'OPENED', '2022-08-03 09:00:00', '2022-08-03 12:00:00', 0, 30, 'test memo4'),
    (2, '행사3', 'CLOSED', '2022-08-04 09:00:00', '2022-08-04 12:00:00', 0, 30, 'test memo5'),
    (4, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (1, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, '오전 스키', 'OPENED', '2022-08-02 08:00:00', '2022-08-02 12:30:00', 12, 50, 'test memo6')
;

insert into `admin` (`email`, `nickname`, `password`, `phone_number`, `memo`,`status`,`login_base`)
values
    ('test@test.com', '테스트', '{noop}1111!', '010-0101-0101', '안녕하세요', 'ACTIVE_USER', 'BASIC_LOGIN'),
    ('admin@gmail.com', 'admin', '{noop}1111!', '010-0101-0999', '안녕하세요', 'ACTIVE_USER', 'BASIC_LOGIN'),
    ('admin@naver.com', 'admin2', '{noop}1111!', '010-0101-0989', '안녕하세요', 'ACTIVE_USER', 'BASIC_LOGIN'),
    ('test1@test.com', 'test11', '{noop}1111!', '010-2222-0981', '안녕하세요', 'INACTIVE_USER', 'BASIC_LOGIN'),
    ('test2@test.com', 'test12', '{noop}1111!', '010-2222-0922', '테스트유저', 'ACTIVE_USER', 'SOCIAL_LOGIN'),
    ('test3@test.com', 'test13', '{noop}1111!', '010-2222-0923', '테스트유저', 'ACTIVE_USER', 'SOCIAL_LOGIN'),
    ('test4@test.com', 'test14', '{noop}1111!', '010-2222-0924', '테스트유저', 'INACTIVE_USER', 'BASIC_LOGIN'),
    ('test5@test.com', 'test15', '{noop}1111!', '010-2222-0925', '테스트유저', 'ACTIVE_USER', 'BASIC_LOGIN'),
    ('test6@test.com', 'test16', '{noop}1111!', '010-2222-0926', '테스트유저', 'INACTIVE_USER', 'BASIC_LOGIN'),
    ('test7@test.com', 'test17', '{noop}1111!', '010-2222-0917', '테스트유저', 'ACTIVE_USER', 'SOCIAL_LOGIN'),
    ('super@test.com', 'test22', '{noop}1111!', '010-2233-0989', '안녕하세요', 'ACTIVE_USER', 'BASIC_LOGIN')
;

insert into `request` (`admin_id`, `request_code`, `message`, `status`)
values
    (1,'ADMIN_REQUEST','관리자 요청합니다!','OPEN_ISSUE'),
    (1,'ADMIN_REQUEST','관리자 요청합니다!','OPEN_ISSUE'),
    (1,'USER_REQUEST','일반 회원 만들어주세요!','OPEN_ISSUE'),
    (2,'ADMIN_REQUEST','관리자 요청합니다!','CLOSE_ISSUE'),
    (2,'USER_INACTIVE_REQUEST','탈퇴해주세요!','IN_PROGRESS_ISSUE'),
    (2,'ADMIN_REQUEST','관리자 요청합니다!','CLOSE_ISSUE'),
    (3,'ADMIN_REQUEST','관리자 요청합니다!','OPEN_ISSUE'),
    (4,'COMPLAIN_REQUEST','불만있습니다!','IN_PROGRESS_ISSUE'),
    (5,'ADMIN_REQUEST','관리자 요청합니다!','OPEN_ISSUE'),
    (5,'QUESTION_REQUEST','문의합니다!','OPEN_ISSUE'),
    (5,'USER_INACTIVE_REQUEST','탈퇴해주세요!','OPEN_ISSUE'),
    (6,'QUESTION_REQUEST','문의합니다!','CLOSE_ISSUE')

;

insert into `admin_roles`(`admin_id`, `roles`)
values
    (1, 'USER'),
    (2, 'ADMIN'),
    (2, 'USER'),
    (3, 'ADMIN'),
    (3, 'USER'),
    (4, 'USER'),
    (5, 'USER'),
    (6, 'USER'),
    (7, 'USER'),
    (8, 'USER'),
    (9, 'USER'),
    (10, 'USER'),
    (11, 'SUPERADMIN'),
    (11, 'ADMIN'),
    (11, 'USER')
    ;

insert into `admin_place_map` (`admin_id`, `place_id`)
values
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (3, 7),
    (3, 8),
    (3, 9),
    (3, 10),
    (3, 11),
    (3, 12)

;