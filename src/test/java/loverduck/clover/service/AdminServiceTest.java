package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.QCompany;
import loverduck.clover.repository.FundingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;


@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminServiceTest {

    @SpyBean
    private UsersServiceImpl usersService;
    @SpyBean
    private FundingRepository fundingRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    JPAQueryFactory qFactory;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        EntityManager em = testEntityManager.getEntityManager();
        qFactory = new JPAQueryFactory(em);
        // here initialize setup for each test
        System.out.println("Before Strat test >>>> " + testInfo.getTestMethod().get().getName());
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        // here initialize setup for each test
        System.out.println("Afetr End test >>>> " + testInfo.getTestMethod().get().getName());
    }


    @Test
    @Rollback(false)
    void addCompany() {
        Company c = Company.builder()
                .address("서울시 강남구")
                .name("테스트")
                .email("test@test.com")
                .type(0)
                .detailAddress("상세주소")
                .homepage("홈페이지")
                .phone("010-1234-5678")
                .funds(null)
                .logo("로고")
                .no("1234")
                .sector("업종")
                .description("설명")
                .build();
        usersService.register2(c);
    }

    @Test
    @Rollback(false)
    void addFunding() {
        Company c = qFactory.selectFrom(QCompany.company)
                .where(QCompany.company.id.eq(3L))
                .fetchOne();

        Funding f = Funding.builder()
                .company(c)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(6))
                .status(0)
                .targetMinAmount(1000000L)
                .targetMaxAmount(1000000L)
                .currentAmount(0L)
                .title("테스트 title")
                .content("테스트 content")
                .dividend(0.1324)
                .build();
        fundingRepository.save(f);
    }
}
