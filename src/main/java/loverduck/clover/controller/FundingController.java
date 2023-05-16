package loverduck.clover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  펀딩 전체 목록 페이지, 펀딩 상세 페이지, 펀딩 투자하기 
 */
@Controller
public class FundingController {

	
	
	/**
	 * 펀딩 전체 목록 페이지
	 */
	@RequestMapping("/fundingList")
	public String fundingList() {
		
		return "/fundingList";
	}
	
	/**
	 * 펀딩 상세 페이지
	 */
	@RequestMapping("/fundingDetail")
	public String fundingDetail() {
		
		return "/fundingDetail";
	}
	
	/**
	 * 펀딩 투자하기 - 약관동의
	 */
	@RequestMapping("/fundingAgree")
	public String fundingAgree() {
		
		return "/fundingAgree";
	}
	
	/**
	 * 펀딩 투자하기 - 펀딩하기
	 * ->KG이니시스 결제창으로 가야한다
	 */
	@RequestMapping("/fundingPay")
	public String fundingPay() {
		
		return "/fundingPay";
	}
	
	/**
	 * 펀딩 투자하기 - 펀딩완료
	 */
	@RequestMapping("/fundingPayFin")
	public String fundingPayFin() {
		
		return "/fundingPayFin";
	}
	
	
}
