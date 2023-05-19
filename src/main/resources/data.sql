-- Funding 테이블
insert into funding (id, content, CURRENT_AMOUNT, DIVIDEND, END_DATE, START_DATE, TARGET_MAX_AMOUNT, TARGET_MIN_AMOUNT, TITLE, COMPANY_ID)
values (1, '2050년 266GW 규모의 시장 잠재력(2050년 Ocean Energy deployment 748GW 중 36% 가정)이 예상됩니다. 이에 532조 원(EPC 매출 시장) + 27조 원/년(O&M 매출 시장)의 시장 잠재력을 예상할 수 있습니다.', 
1, 10.0, '2023-06-14', '2023-05-14', 50000, 10000, '파도, 인진에서 "파력 에너지"로 다시 태어나다', 1);

-- Company 테이블 
insert into company (id, address, description, email, homepage, logo, name, no, phone, sector)
values (1, '경기 성남시 분당구 판교로 242 PDC A동 504호', 
'(주)인진은 파력을 이용해 전기를 생산하는 파력발전 벤처기업입니다. (주)인진의 기술성숙도는 TRL(Technology Readiness Level) 7단계로, 현재 Pre-Commercial 단계(상용화 단계는 TRL 9단계)에 도달하였습니다. 이를 통해 현재 프랑스, 스리랑카, 인도네시아, 영국, 캐나다 진출을 위해 활발히 진행 중이며, 파력발전의 거대한 시장잠재력을 기반으로 수익 급증을 기대하고 있습니다.',
'testcom@test.com', 'www.test.com', 'test.jpg', '인진', '1', '02-000-000', '에너지');