package loverduck.clover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 회원가입 , 로그인 , 마이페이지 
 */
@Controller
public class UserController {	
	
	/**
	 * 회원가입 - 투자자 폼
	 */
	@RequestMapping("/registerInvestor")
	public String registerInvestor() {
		
		return "registerInvestor";
	}
	
	/**
	 * 회원가입 - 기업 폼
	 */
	@RequestMapping("/registerCorp")
	public String registerCorp() {
		
		return "registerCorp";
	}
	
	/**
	 * 회원가입 투자자
	 */
	@RequestMapping("/reigster")
	public String register() {
		return "redirect:/";
	}
	
	/**
	 * 회원가입 기업
	 */
	@RequestMapping("/reigster2")
	public String register2() {
		return "redirect:/";
	}
	
	
	/**
	 * 로그인 폼
	 */
	@RequestMapping("/loginForm")
	public String login() {

		return "login";
	}
	
	/**
	 * 로그인하기
	 * post -> 페이지 전환 없이 값만 전달
	 * 
	 */
	@RequestMapping("/loginCheck")
	public String loginCheck() {
		
		return "redirect:/";
	}
	
	/**
	 * 로그아웃 하기
	 */
	@RequestMapping("/logout")
	public String logout() {
		
		return "redirect:/";
	}
	
	
	/**
	 * 마이페이지 - 투자자 (개인정보 수정폼)
	 */
	@RequestMapping("/updateInvestor")
	public String updateInvestor() {
		return "mypage/updateInvestor";
	}
	
	/**
	 * 마이페이지 - 기업 (개인정보 수정폼)
	 */
	@RequestMapping("/updateCorp")
	public String updateCorp() {
		return "mypage/updateCorp";
	}
	
	/**
	 * 마이페이지 - 투자자 (내 펀딩)
	 */
	@RequestMapping("/mypageInvestor")
	public String mypageInvestor() {
		
		return "mypage/mypageInvestor";
	}
	
	/**
	 * 마이페이지 - 기업 (내 펀딩) //펀딩 현황?
	 */
	@RequestMapping("/mypageCorp")
	public String mypageCorp() {
		
		return "mypage/mypageCorp";
	}
	
	///////////////////////////////////////////////////////////////

	/**
	 * 마이페이지 - 투자자 (거래 내역)
	 */
	@RequestMapping("/historyInvestor")
	public String historyInvestor() {
		
		return "mypage/historyInvestor";
	}


	/**
	 * 마이페이지 - 투자자 (정산) - 페이지 레이아웃 미완료
	 */

	/**
	 * 마이페이지 - 투자자 (포인트 충전)
	 */
	@RequestMapping("/charge")
	public String pointCharge() {
		
		return "mypage/pointCharge";
	}
	
	/**
	 * 마이페이지 - 투자자 (포인트 충전완료)
	 */
	@RequestMapping("/chargeFin")
	public String pointChargeFin() {
		
		return "mypage/pointChargeFin";
	}
	
	//////////////////////////////////////
	
	/**
	 * 마이페이지 - 투자자 & 기업 (문의 내역)
	 */
	@RequestMapping("/historyAsk")
	public String historyAsk() {
		
		return "mypage/historyAsk";
	}

	/**
	 * 마이페이지 - 투자자 & 기업 (문의 내역 > 문의하기)
	 */
	@RequestMapping("/ask")
	public String doAsk() {
		
		return "mypage/ask";
	}
	
	/**
	 * 마이페이지 - 기업 펀드 신청
	 */
	@RequestMapping("/fundSubmitForm")
	public String fundSubmitForm() {
		
		return "mypage/fundSubmitForm";
	}
	
	/**
	 * 마이페이지 - 기업 펀드 신청 (등록하기)
	 * insert 펀드를 등록시킨다. ->등록되면 내역으로 보내기
	 */
	@RequestMapping("/fundSubmit")
	public String fundSubmit() {
		
		return "mypage/historyCorp";
	}
	
	/**
	 * 마이페이지 - 기업 (펀딩 신청) - 펀딩 신청 내역
	 * 펀드 신청 조회
	 */
	@RequestMapping("/historyCorp")
	public String historyCorp() {
		
		return "mypage/historyCorp";
	}
	
	/**
	 * 마이페이지 - 기업 (정산) - 페이지 레이아웃 미완료
	 */
	
	/**
	 * 마이페이지 - 기업 (포인트)  - 페이지 레이아웃 미완료 (투자자랑 비슷)
	 */
	@RequestMapping("/pointCorp")
	public String pointCorp() {
		
		return "mypage/pointCharge";
	}
	
	
}
