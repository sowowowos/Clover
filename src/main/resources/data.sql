-- User 테이블 
insert into users (id, userid, password, email, nickname, type)
values(1, 'ererink', '1234', 'ererink@gmail.com', '흑석동핵주먹', 1);

-- Company 테이블 
insert into company (id, address, description, email, homepage, logo, name, no, phone, sector)
values (1, '경기 성남시 분당구 판교로 242 PDC A동 504호', 
'파력을 이용해 전기를 생산하는 파력발전 벤처기업',
'testcom@test.com', 'www.test.com', 'test.jpg', '인진', '1', '02-000-000', '에너지');

insert into company (id, address, description, email, homepage, logo, name, no, phone, sector)
values (2, '서울특별시 동작구 현충로 119 위워크 304', 
'시타, 남기고자 하는 것들에 대한 기록',
'sitta@sitta.com', 'www.sitta.com', 'sitta.jpg', 'sitta', '2', '02-465-1902', '뷰티');

-- Funding 테이블
insert into funding (id, content, CURRENT_AMOUNT, DIVIDEND, END_DATE, START_DATE, TARGET_MAX_AMOUNT, TARGET_MIN_AMOUNT, TITLE, COMPANY_ID)
values (1, '2050년 266GW 규모의 시장 잠재력(2050년 Ocean Energy deployment 748GW 중 36% 가정)이 예상됩니다. 이에 532조 원(EPC 매출 시장) + 27조 원/년(O&M 매출 시장)의 시장 잠재력을 예상할 수 있습니다.', 
1, 10.0, '2023-06-14', '2023-05-14', 50000, 10000, '파도, 인진에서 "파력 에너지"로 다시 태어나다', 1);


insert into funding (id, content, CURRENT_AMOUNT, DIVIDEND, END_DATE, START_DATE, TARGET_MAX_AMOUNT, TARGET_MIN_AMOUNT, TITLE, COMPANY_ID)
values (2, '우리는 우리 모두의 고향 지구가 죽지 않도록 하기 위해 사업을 합니다.
우리는 다음 세대를 위해 세상에 아무것도 남기지 않기 위해 노력합니다.
우리가 세상에 남겨도 되는 것은 많지 않기 때문입니다.', 
2, 20.0, '2023-04-23', '2023-03-23', 7800000, 2350000, '시타, 남기고자 하는 것들에 대한 기록', 2);

insert into funding (id, content, CURRENT_AMOUNT, DIVIDEND, END_DATE, START_DATE, TARGET_MAX_AMOUNT, TARGET_MIN_AMOUNT, TITLE, COMPANY_ID)
values (3, '스웨덴을 대표하는 차세대 초현실주의 사진 작가, 에릭 요한슨은 토요타, 볼보, 구글 , 어도비 등 다양한 글로벌 기업과의 콜라보레이션을 통해 검증된 아티스트입니다. 
그는 마그리트의 작품에서 영향을 받았으며, 달리와 에셔와 같은 거장들의 꿈을 재현한 듯한 비현실적인 모습의 작품 활동을 합니다. ', 
3, 23.0, '2023-08-22', '2023-10-07', 2430000, 2350000, '스웨덴 대표 사진작가,에릭 요한슨 사진전', 1);


-- Funding_Reply 테이블
-- insert into funding_reply (id, 
commit;