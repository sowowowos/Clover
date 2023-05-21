package loverduck.clover.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import loverduck.clover.entity.Users;

import loverduck.clover.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {

	
	@Autowired
	private UsersRepository usersRep;
	
	
	@Override
	public int register(Users users) {
		
		Users dbUser = usersRep.save(users);
		
		//userDetail.setUser(dbUser); //부모의 key(id)참조 
		//userDetailRep.save(userDetail);
		
		return 1;
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



	



	

}
