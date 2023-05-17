package loverduck.clover.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.querydsl.jpa.impl.JPAQueryFactory;

import loverduck.clover.repository.ExchangeRepository;
import loverduck.clover.repository.FundingRepository;
import loverduck.clover.repository.PointHistoryRepository;
import loverduck.clover.repository.WalletRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointHistoryTest {

	@Autowired
	private PointHistoryRepository pointHistoryRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private ExchangeRepository exchangeRepository;
	@Autowired
	private FundingRepository fundingRepository;

	@Autowired
	TestEntityManager testEntityManager;
	JPAQueryFactory qFactory;

	@BeforeEach
	void beforeEach(TestInfo testInfo) {
		EntityManager em = testEntityManager.getEntityManager();
		qFactory = new JPAQueryFactory(em);
		// here initialize setup for each test
		System.out.println("Before Start test >>>> " + testInfo.getTestMethod().get().getName());
	}

	@AfterEach
	void afterEach(TestInfo testInfo) {
		// here initialize setup for each test
		System.out.println("After End test >>>> " + testInfo.getTestMethod().get().getName());
	}
	
	@Test
	// @Rollback(value = false)
	@DisplayName("id가 1인 지갑에 테스트 포인트 추가")
	void insertChargePoint() {
		LocalDateTime dateTime = LocalDateTime.now();

		// Given
		Wallet wid = walletRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

		// Funding f = fundingRepository.findById(1L)
		// .orElseThrow(() -> new IllegalArgumentException("Funding not found"));

		PointHistory ph = PointHistory.builder()
						.amount(100000L)
						.type(0)
						.createdAt(dateTime)
						.wallet(wid)
						.build();

		// When
		// 테스트할 동작 수행 (생략)
		pointHistoryRepository.save(ph);

		// Then
		// 검증 로직 작성 (생략)
	}

	// 포인트 충전시 wallet 잔액 변경 - updateAmount
	// 포인트 사용(-)
	// 포인트 충전(+)
	@Test
	@Rollback(value = false)
	@DisplayName("id가 1인 지갑에 테스트 포인트 사용")
	void updateChargePoint() {
		Long amount = 10000L;

		// Given
		Wallet wid = walletRepository.findById(1L)
				.orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

		EntityManager entityManager = testEntityManager.getEntityManager();
		qFactory = new JPAQueryFactory(entityManager);
		
		QPointHistory qPointHistory = QPointHistory.pointHistory;

		// 가장 마지막 PointHistory 조회
		PointHistory lastPointHistory = qFactory.selectFrom(qPointHistory)
								.where(qPointHistory.wallet.eq(wid))
								.orderBy(qPointHistory.createdAt.desc())
								.fetchFirst();

		//기존 잔액 + 충전금액
		long updateAmount = lastPointHistory.getAmount() - amount;
		
		qFactory.update(qPointHistory)
				.set(qPointHistory.amount, updateAmount)
				.where(qPointHistory.id.eq(lastPointHistory.getId()))
				.execute();

		// When
		// 테스트할 동작 수행 (생략)
		// Then
		// 검증 로직 작성 (생략)
	}

	

}
