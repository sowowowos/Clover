package loverduck.clover.entity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import loverduck.clover.repository.UsersRepository;
import loverduck.clover.repository.WalletRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalletTest {
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	TestEntityManager testEntityManager;
	
	
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
	//@Rollback(false)
	@DisplayName("test 사용자 추가")
	void insertDatabaseUsers() {
		Users user = Users.builder()
				.userid("test")
				.email("example@example.com")
				.password("1234")
				.nickname("test")
				.type(0).build();
		usersRepository.save(user);
	}

	@Test
	//@Rollback(value = false)
	@DisplayName("test 이름을 가진 사용자에게 지갑 추가")
	void insertDatabaseWallet() {
		Users u = qFactory.selectFrom(QUsers.users)
				.where(QUsers.users.userid.eq("test"))
				.fetchOne();
		Wallet wallet = Wallet.builder()
				.user(u)
				.amount(1000000L)
				.build();
		walletRepository.save(wallet);
	}
	
	@Test
	@Rollback(value = false)
	@DisplayName("wallet의 recentAmount에서 amount만큼 출금")
	void recentAmountWallet() {
		
		Wallet wallet = walletRepository.findById(1L)
				.orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
		
		System.out.println("wid.getId() = " + wallet.getId() );
		
		QWallet qWallet = QWallet.wallet;

		long recentAmount = qFactory.select(qWallet.amount)
				.from(qWallet)
				.where(qWallet.id.eq(wallet.getId()))
				.fetchOne();
		
		System.out.println("recentAmount = " + recentAmount);
		
		Long amount = 5000L;
		
		//기존 잔액 + 충전금액
		long updateAmount = recentAmount - amount;
		
		System.out.println("updateAmount = " + updateAmount);
		
		qFactory.update(qWallet)
		.set(qWallet.amount, updateAmount)
		.where(qWallet.id.eq(1L))
		.execute();		
	}
	

//	@Test
//	@Rollback(value = false)
//	@DisplayName("wallet에서 가장 최근 point amount를 현재 잔액으로 표시")
//	public void recentAmountWallet() {
//		QPointHistory qPointHistory = QPointHistory.pointHistory;
//		QWallet qWallet = QWallet.wallet;
//
//		PointHistory lastPointHistory = qFactory.selectFrom(qPointHistory)
//				        .innerJoin(qPointHistory.wallet, qWallet)
//				        .where(qWallet.id.eq(1L))
//				        .orderBy(qPointHistory.createdAt.desc())
//				        .fetchFirst();
//
//		// 최근 PointHistory의 amount를 Wallet 테이블의 amount 값으로 설정
//	    Wallet wallet = lastPointHistory.getWallet();
//	    Long latestAmount = lastPointHistory.getAmount();
//
//	    wallet.setAmount(latestAmount);
//	    walletRepository.save(wallet);
//	}
	
	
}


