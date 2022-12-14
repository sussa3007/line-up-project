

insert into `place` (`place_type`, `admin_email`,`place_name`, `address`, `phone_number`, `capacity`, `memo`)
values
    ('COMMON', 'super@test.com','test', 'aaadfga', '010-1111-2222', 1, 'test'),
    ('SPORTS', 'admin@gmail.com','스포츠 센터', '경기도 성남시 판교로 999', '010-9999-0000', 20, '스포츠 센터 예약 가능'),
    ('RESTAURANT', 'admin@gmail.com','스프링 식당', '서울시 강남구 가나대로 123', '010-1234-5678', 10, '테스트 메모'),
    ('SPORTS', 'admin@gmail.com','스프링 스키장', '하늘 천국 천국로 555', '010-1004-1004', 9000, null),
    ('COMMON', 'admin@gmail.com','스프링 컴퍼니', '111, Gana-ro, Gangnam-gu, Seoul', '010-1111-1111', 50, '회사 방문 가능'),
    ('PARTY', 'admin@gmail.com','스프링 파티룸', '서울시 강남구 가나대로 123 2층', '010-1234-5678', 10, '파티룸 예약 가능'),
    ('PARTY', 'admin@gmail.com','자바 파티룸', '부산시 중구 가나대로 123 2층', '010-1234-5671', 30, '파티룸 예약 가능'),
    ('COMMON','admin@naver.com', '자바 컴퍼니', '부산시 해운대 가나대로 123 2층', '010-1234-5672', 20, '가족같은 분위기'),
    ('SPORTS','admin@naver.com', '자바 스포츠 센터', '부산시 사상구 가나대로 123 2층', '010-1234-5673', 30, '스쿼시 ㄱㄱ'),
    ('COMMON','admin@naver.com', '코딩 컴퍼니', '서울시 동작구 가나대로 123 2층', '010-1234-5674', 20, '가족같은 회사'),
    ('RESTAURANT','admin@naver.com', '자바 식당', '서울시 강서구 가나대로 123 2층', '010-1234-5675', 10, '먹방ㄱㄱ'),
    ('PARTY','admin@naver.com', '코딩 파티룸', '부산시 강서구 가나대로 123 2층', '010-1234-5676', 50, '술파티 ㄱ'),
    ('SPORTS','admin@naver.com', '코딩 테니스장', '제주도 서귀포시 가나대로 123 2층', '010-1234-5677', 9000, '테니스 한겜')
;

insert into `event` (`place_id`, `event_name`, `event_status`, `event_start_datetime`, `event_end_datetime`, `current_number_of_people`, `capacity`, `memo`)
values
    (13, '운동1', 'OPENED', '2022-08-01 09:00:00', '2022-08-01 12:00:00', 0, 20, 'test memo1'),
    (13, '운동2', 'OPENED', '2022-08-01 13:00:00', '2022-08-01 12:00:00', 0, 20, 'test memo2'),
    (2, '행사1', 'OPENED', '2022-08-02 09:00:00', '2022-08-02 12:00:00', 0, 30, 'test memo3'),
    (2, '행사2', 'OPENED', '2022-08-03 09:00:00', '2022-08-03 12:00:00', 0, 30, 'test memo4'),
    (2, '행사3', 'CLOSED', '2022-08-04 09:00:00', '2022-08-04 12:00:00', 0, 30, 'test memo5'),
    (4, 'test event', 'ABORTED', '2022-09-01 18:00:00', '2022-09-03 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-06 18:00:00', '2022-09-08 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-29 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'ABORTED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (6, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (6, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (6, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (7, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (8, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (7, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (7, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (8, 'test event', 'CANCELLED', '2022-09-30 18:00:00', '2022-09-30 20:00:00', 0, 10, 'test'),
    (9, '오전 스키', 'OPENED', '2022-10-02 08:00:00', '2022-10-02 12:30:00', 12, 50, 'test memo6'),
    (9, '운동1', 'OPENED', '2022-10-01 09:00:00', '2022-10-01 12:00:00', 0, 20, 'test memo1'),
    (9, '운동2', 'OPENED', '2022-10-01 13:00:00', '2022-10-01 12:00:00', 0, 20, 'test memo2'),
    (10, '행사1', 'OPENED', '2022-10-02 09:00:00', '2022-10-02 12:00:00', 0, 30, 'test memo3'),
    (10, '행사2', 'OPENED', '2022-10-03 09:00:00', '2022-10-03 12:00:00', 0, 30, 'test memo4'),
    (10, '행사3', 'CLOSED', '2022-10-04 09:00:00', '2022-10-04 12:00:00', 0, 30, 'test memo5'),
    (11, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (11, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (11, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (12, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (12, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (12, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (13, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (13, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (2, 'test event', 'ABORTED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (3, 'test event', 'CANCELLED', '2022-10-30 18:00:00', '2022-10-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (4, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (5, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (6, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (6, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (7, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (8, 'test event', 'CANCELLED', '2022-11-30 18:00:00', '2022-11-30 20:00:00', 0, 10, 'test'),
    (9, '오전 스키', 'OPENED', '2022-12-02 08:00:00', '2022-12-02 12:30:00', 12, 50, 'test memo6')
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

insert into `post`(`title`, `post`,`admin_id`, `status`, `password`,`place_id`)
values
    ('[공지사항] 안녕하세요 반갑습니다!!!!!', 'aaasdg', 11, 'NOTICE', '1111',1),
    ('[장소 공지사항] 안녕하세요 반갑습니다!!!!!', 'aaasdg', 2, 'PLACE_NOTICE', '1111',2),
    ('[장소 공지사항] 안녕하세요 반갑습니다!!!!!', 'aaasdg', 2, 'PLACE_NOTICE', '1111',2),
    ('[공지사항] 안녕하세요 반갑습니다!!!!!', 'aaasdg', 11, 'NOTICE', '1111',1),
    ('[메세지] 안녕하세요 반갑습니다!!!!!', 'aaasdg', 1, 'MESSAGE', '1111',1),
    ('[메세지] 안녕하세요 반갑습니다!!!!!', '<h1>Sample Markdown</h1>
<p>Hello!
공지사항 테스트 입니다!!</p>
<p>This is some basic, sample markdown.aghslkdfhklashdglkahsl 줄바꿈 테스트kdqklsadhflk;ahslaskdflhashdklfhalksdhfklhskldhflkshdklfhalksdhfklaskldhfklasdk;gadsklfalkdshflkahsdlkfsfhasdfagwafasdavsdvlak"sdhlk</p>
<ul>
<li>공지사항</li>
<li>장소 공지사항</li>
</ul>
<h2>Second Heading</h2>
<ul>
<li>Unordered lists,
<ol>
<li>One</li>
<li>Two</li>
<li>Three</li>
</ol>
</li>
<li>More</li>
</ul>
<blockquote>
<p>Blockquote</p>
</blockquote>
<p>And <strong>bold</strong>, <em>italics</em>, and even <em>italics and later <strong>bold</strong></em>. Even strikethrough. <a href="https://markdowntohtml.com">A link</a> to somewhere.</p>
<ul>
<li>코드블럭</li>
</ul>
<pre><code>var foo = hello;

                            function baz(s) {
                            return foo + &quot;:&quot; + s;
                            }

</code></pre>
<p>Or inline code like <code>var foo = hello;</code>.</p>
<ul>
<li>이미지 셈플</li>
</ul>
<p><img src="https://user-images.githubusercontent.com/110886399/206844039-58b24212-abfb-42f5-b570-70c5c311898c.png" alt="image" /></p>
<p>The end ...</p>
', 1, 'MESSAGE', '1111',1),
    ('[장소 공지사항] 안녕하세요 반갑습니다!!!!!', '<h1>Sample Markdown</h1>
<p>Hello!
공지사항 테스트 입니다!!</p>
<p>This is some basic, sample markdown.aghslkdfhklashdglkahsl 줄바꿈 테스트kdqklsadhflk;ahslaskdflhashdklfhalksdhfklhskldhflkshdklfhalksdhfklaskldhfklasdk;gadsklfalkdshflkahsdlkfsfhasdfagwafasdavsdvlak"sdhlk</p>
<ul>
<li>공지사항</li>
<li>장소 공지사항</li>
</ul>
<h2>Second Heading</h2>
<ul>
<li>Unordered lists,
<ol>
<li>One</li>
<li>Two</li>
<li>Three</li>
</ol>
</li>
<li>More</li>
</ul>
<blockquote>
<p>Blockquote</p>
</blockquote>
<p>And <strong>bold</strong>, <em>italics</em>, and even <em>italics and later <strong>bold</strong></em>. Even strikethrough. <a href="https://markdowntohtml.com">A link</a> to somewhere.</p>
<ul>
<li>코드블럭</li>
</ul>
<pre><code>var foo = hello;

                            function baz(s) {
                            return foo + &quot;:&quot; + s;
                            }

</code></pre>
<p>Or inline code like <code>var foo = hello;</code>.</p>
<ul>
<li>이미지 셈플</li>
</ul>
<p><img src="https://user-images.githubusercontent.com/110886399/206844039-58b24212-abfb-42f5-b570-70c5c311898c.png" alt="image" /></p>
<p>The end ...</p>
', 2, 'PLACE_NOTICE', '1111',2)
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
    (11, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7),
    (3, 8),
    (3, 9),
    (3, 10),
    (3, 11),
    (3, 12),
    (3, 13)

;