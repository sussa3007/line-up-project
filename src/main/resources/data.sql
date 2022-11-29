

insert into `place` (`place_type`, `place_name`, `address`, `phone_number`, `capacity`, `memo`)
values
    ('SPORTS', '스포츠 센터', '경기도 성남시 판교로 999', '010-9999-0000', 20, '스포츠 센터 예약 가능'),
    ('RESTAURANT', '스프링 식당', '서울시 강남구 가나대로 123', '010-1234-5678', 10, '테스트 메모'),
    ('SPORTS', '스프링 스키장', '하늘 천국 천국로 555', '010-1004-1004', 9000, null),
    ('COMMON', '스프링 컴퍼니', '111, Gana-ro, Gangnam-gu, Seoul', '010-1111-1111', 50, '회사 방문 가능'),
    ('PARTY', '스프링 파티룸', '서울시 강남구 가나대로 123 2층', '010-1234-5678', 1, '파티룸 예약 가능')
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

insert into `admin` (`email`, `nickname`, `password`, `phone_number`, `memo`)
values
    ('test@test.com', '테스트', '{noop}1111!', '010-0101-0101', '안녕하세요'),
    ('admin@gmail.com', 'admin', '{noop}1111!', '010-0101-0999', '안녕하세요'),
    ('admin@naver.com', 'admin2', '{noop}1111!', '010-0101-0989', '안녕하세요')
;

insert into `admin_roles`(`admin_id`, `roles`)
values
    (1, 'USER'),
    (2, 'ADMIN'),
    (2, 'USER'),
    (3, 'ADMIN'),
    (3, 'USER')
    ;

insert into `admin_place_map` (`admin_id`, `place_id`)
values
    (2, 1),
    (2, 2),
    (2, 3),
    (3, 4),
    (3, 5)

;