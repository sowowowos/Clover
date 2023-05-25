package loverduck.clover.service;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.Ordered;
import loverduck.clover.entity.Users;
import loverduck.clover.entity.Wallet;
import loverduck.clover.repository.CompanyRepository;
import loverduck.clover.repository.OrderedRepository;
import loverduck.clover.repository.UsersRepository;
import loverduck.clover.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRep;
    private final CompanyRepository companyRep;
    private final OrderedRepository orderedRepository;
    private final WalletRepository walletRep;


    @Override
    public int register(Users users) {

        Users dbUser = usersRep.save(users);

        ///////////wallet 체크해보자 null
        Wallet wallet = Wallet.builder()
                .user(users)
                .amount(0L)
                .build();
        walletRep.save(wallet);

        usersRep.updateWallet(wallet, dbUser.getEmail());

        return 1;
    }


    @Override
    public int register2(Company company) {

        companyRep.save(company);

        return 1;
    }


    @Override
    public Company findCompany(String email) {
        Company company = companyRep.findByCEmail(email);

        return company;
    }


    @Override
    public Users logincheck(String email, String password) {

        Users dbUser = usersRep.findByEmail(email);


        if (dbUser == null) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

        if (!dbUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");
        }

        return dbUser;
    }


    /**
     * 진짜 - 이메일 중복 체크
     */
    @Override
    public boolean checkEmailExists(String email) {
        Users users = usersRep.findByEmail(email);

        return users != null;
    }


    @Override
    public Users update(String password, String nickname, String phone, String postalCode, String address,
                        String detailAddress, String email) {

        usersRep.updateUser(nickname, password, phone, postalCode, address, detailAddress, email);

        Users dbUser = usersRep.findByEmail(email);

        return dbUser;

    }


    //카톡 투자자 로그인시 추가 정보 업데이트
    @Override
    public Users updateAll(String name, String nickname, Integer type, String phone, String postalCode, String address,
                           String detailAddress, String email) {

        usersRep.updateUserAll(name, nickname, type, phone, postalCode, address, detailAddress, email);

        Users dbUser = usersRep.findByEmail(email);

        return dbUser;
    }


    /**
     * 마이페이지 - 내가 투자한 펀딩 목록 출력
     */
    @Override
    public List<Ordered> findOrderdByUser(Long id) {
        List<Ordered> myFunds = orderedRepository.findFundingsByUserId(id);
        return myFunds;
    }


}
