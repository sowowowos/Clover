package loverduck.clover.service;

import loverduck.clover.entity.UserDetail;
import loverduck.clover.entity.Users;

public interface UsersService {
	
	
	/**
	 * 회원가입하기
	 */
	int register(Users users,UserDetail userDetail);
	
	/**
	 * 로그인채크
	 */
	Users logincheck(String email, String password);
	
	/**
	 * 로그인
	 */
//	Users findByEmail(String email);

	
	/**
	 * 진짜 - 이메일 중복 체크
	 * @param email
	 * @return
	 */
	boolean checkEmailExists(String email);
	

}
