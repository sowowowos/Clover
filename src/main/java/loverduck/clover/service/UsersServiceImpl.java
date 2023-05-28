package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.*;
import loverduck.clover.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRep;
    private final CompanyRepository companyRep;
    private final WalletRepository walletRep;
    private final FundingRepository fundingRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int register(Users users) {

        Users dbUser = usersRep.save(users);

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
    public Users updateAll(String name, String nickname, String imgProfile, Integer type, String phone, String postalCode, String address,
                           String detailAddress, String email) {

        usersRep.updateUserAll(name, nickname, imgProfile, type, phone, postalCode, address, detailAddress, email);

        Users dbUser = usersRep.findByEmail(email);

        return dbUser;
    }

    /**
     * 마이페이지 - 내가 투자한 펀딩 목록 출력
     */
//    @Override
//    public List<Funding> findMyFundingsByUserId(Long user_id) {
//        List<Funding> myFundings = fundingRepository.findMyFundingsByUserId(user_id);
//        return myFundings;
//    }

    /**
     * 마이페이지 - 내가 투자한 현재 진행 중인 펀딩 목록 출력
     */

    @Override
    public List<Funding> findMyFundingsByUserId(Users user) {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory.selectFrom(QOrdered.ordered)
                .select(QOrdered.ordered.funding)
//        		.where(QOrdered.ordered.funding.startDate.after(now))
//        		.where(QOrdered.ordered.funding.endDate.before(now))
                .where(QOrdered.ordered.user.eq(user))
                .fetch();
    }

    @Override
    public void mappingCompanyUser(Users user, Company company) {
        jpaQueryFactory.update(QUsers.users)
                .where(QUsers.users.id.eq(user.getId()))
                .set(QUsers.users.company, company)
                .execute();
    }

    /**
     * 마이페이지 - 배당 내역 출력 (정산)
     */
    public List<PointHistory> allocationHistoryInvestor(Long wallet_id) {
//		System.out.println("user_id 1" + wallet_id);
        List<PointHistory> allocations = pointHistoryRepository.findAllocationsByUserId(wallet_id);
        return allocations;
    }


}
