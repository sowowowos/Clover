package loverduck.clover.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Funding;
import loverduck.clover.service.FundingService;

/**
 *  펀딩 전체 목록 페이지, 펀딩 상세 페이지, 펀딩 투자하기 
 */
@Controller
@RequiredArgsConstructor
public class FundingController {

	@Autowired
	private final FundingService fundingService;
	
	/**
	 * 펀딩 전체 목록 페이지
	 */
	@RequestMapping("/fundingList")
	public String fundingList(Model model) {
		List<Funding> fundingList = fundingService.fundingList();
        model.addAttribute("fundingList", fundingList);
		return "/fundingList";
	}
	
	/**
	 * 펀딩 상세 페이지
	 */
	@RequestMapping("/fundingDetail/{id}")
	public String fundingDetail(@PathVariable Long id, Model model) {
		Funding fund = fundingService.fundingDetail(id);
		model.addAttribute("fund", fund);
		
//		List<Funding> nowFunds = fundingService.findByCompanyName(fund.getCompany());
		System.out.println(fund.getCompany().getFunds());
		model.addAttribute("nowFunds", fund.getCompany().getFunds());
		
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
