package loverduck.clover.service;


import loverduck.clover.entity.Company;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Users;

import java.util.List;

public interface UsersService {


    /**
     * 회원가입하기
     */
    int register(Users users);


    /**
     * 회원가입하기 - company 속성
     */
    int register2(Company company);

    /**
     * 이메일로 company 찾기
     */
    Company findCompany(String email);

    /**
     * 로그인채크
     */
    Users logincheck(String email, String password);


    /**
     * 진짜 - 이메일 중복 체크
     */
    boolean checkEmailExists(String email);

    /**
     * 수정하기 - 비밀번호, 닉네임, 폰, 주소3개
     */
    Users update(String password, String nickname, String phone, String postalCode, String address, String detailAddress, String email);

    /**
     * 수정하기 - 카톡 투자자 회원일때
     */
    Users updateAll(String name, String nickname, String imgProfile, Integer type, String phone, String postalCode, String address, String detailAddress, String email);

    /**
     * 마이페이지 투자한 펀딩 목록 출력
     */
    //List<Funding> findMyFundingsByUserId(Long user_id);

    /**
     * 마이페이지 배당 내역 출력 (정산)
     */
    List<PointHistory> allocationHistoryInvestor(Long wallet_id);

    List<Funding> findMyFundingsByUserId(Users user);

    /**
     * 회사 회원 정보 매핑
     */
    void mappingCompanyUser(Users user, Company company);

}
