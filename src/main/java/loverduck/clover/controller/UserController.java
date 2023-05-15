package loverduck.clover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * 로그인 폼
	 */
	@RequestMapping("/")
	public String login() {
		System.out.println("userController login() call");
		return "login";
	}
	
	
	
}
