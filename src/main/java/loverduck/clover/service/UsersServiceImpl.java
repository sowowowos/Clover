package loverduck.clover.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.Users;
import loverduck.clover.repository.CompanyRepository;
import loverduck.clover.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {

	
	@Autowired
	private UsersRepository usersRep;
	
	@Autowired
	private CompanyRepository companyRep;
	
	
	@Override
	public int register(Users users) {
		
		Users dbUser = usersRep.save(users);
		
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
		
		return users!= null;
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






	

}