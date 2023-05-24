package loverduck.clover.entity;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;
import loverduck.clover.repository.CompanyRepository;
import loverduck.clover.repository.FundingRepository;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FundingTest {
	
	@Autowired
    private FundingRepository fundingRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
    TestEntityManager testEntityManager;
    JPAQueryFactory qFactory;
    
    @Test
    @Rollback(false)
    @DisplayName("test 펀딩 이름 추가")
    @Disabled
    void insertDatabaseFunding() {
    	Funding fund = Funding.builder()
    			.title("test")
    			.content("test test test test test")
    			.targetMinAmount(10000L)
    			.targetMaxAmount(500000L)
    			.currentAmount(1L)
//    			.startDate(LocalDateTime.of(2023, 5, 14, 0, 0))
//    			.endDate(LocalDateTime.of(2023, 6, 14, 0, 0))
    			.dividend(100.0)
//    			.company(Company com = Company.builder() 
//    							.no("1")
//    							.name("test")
//    							.address("test test")
//    							.phone("010-0000-0000")
//    							.email("test@gamil.com")
//    							.homepage("www.test.com")
//    							.description("test test test")
//    							.logo("test.img")
//    							.type(1)
//    							.sector("test")
//    							
//    					)
    			.build();
    	fundingRepository.save(fund);
    }
    
}
