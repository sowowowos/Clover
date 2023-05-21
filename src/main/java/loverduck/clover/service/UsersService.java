package loverduck.clover.service;


import loverduck.clover.entity.Users;

public interface UsersService {
	
	
	/**
	 * 회원가입하기
	 */
	int register(Users users);
	
	/**
	 * 로그인채크
	 */
	Users logincheck(String email, String password);
	

	/**
	 * 진짜 - 이메일 중복 체크
	 */
	boolean checkEmailExists(String email);
	

}
