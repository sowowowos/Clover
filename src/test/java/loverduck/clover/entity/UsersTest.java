package loverduck.clover.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.repository.UserDetailRepository;
import loverduck.clover.repository.UsersRepository;
import loverduck.clover.repository.WalletRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

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
//    @Rollback(false)
    @DisplayName("test 사용자 추가")
    void insertDatabaseUsers() {
        Users user = Users.builder()
                .userid("test")
                .email("example@example.com")
                .password("1234")
                .nickname("test")
                .type(0)
                .build();
        usersRepository.save(user);
    }

    @Test
//    @Rollback(value = false)
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
//    @Rollback(value = false)
    @DisplayName("test 사용자의 세부정보 설정")
    void insertDatabaseUserDetail() {
        Users u = qFactory.selectFrom(QUsers.users)
                .where(QUsers.users.userid.eq("test"))
                .fetchOne();
        UserDetail ud = UserDetail.builder()
                .user(u)
                .address("Seoul, Korea")
                .detailAddress("testLocation")
                .name("Testuser Kim")
                .phone("010-0000-0000")
                .postalCode("00000")
                .build();
        userDetailRepository.save(ud);
    }
}
