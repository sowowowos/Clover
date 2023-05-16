package loverduck.clover.entity;

import lombok.extern.slf4j.Slf4j;
import loverduck.clover.repository.UserDetailRepository;
import loverduck.clover.repository.UsersRepository;
import loverduck.clover.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    @Test
    void insertDatabaseUsers() {
//        for (int i = 0; i < 100; i++) {
        Wallet w = Wallet.builder().amount(0L).build();
        walletRepository.save(w);
        UserDetail ud = UserDetail.builder().build();
        userDetailRepository.save(ud);
        Users user = Users.builder()
                .userid("test${i}")
                .password("test${i}")
                .email("test${i}@test.com")
                .nickname("test")
                .type(0)
                .wallet(w)
                .company(null)
                .build();
        Users u = usersRepository.save(user);
//        }


        Users findUser = usersRepository.findById(u.getId()).orElse(null);

        if (findUser != null) log.info(findUser.getEmail());
        else log.info("NULL!!!!");
    }
}
