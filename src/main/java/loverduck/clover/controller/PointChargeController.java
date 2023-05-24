package loverduck.clover.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Users;
import loverduck.clover.entity.Wallet;
import loverduck.clover.service.PointHistoryService;
import loverduck.clover.service.WalletService;

@Controller
@RequestMapping("/mypage")
public class PointChargeController{
	@Autowired
	PointHistoryService pointHistoryService;
	
	@Autowired
	WalletService walletService;
	
	@ModelAttribute("user")
    public Users getUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }
	
	/**
	 *  MYPAGE - 포인트 충전 
	 */
	@GetMapping(value = "/pointCharge")
	public String pointCharge(Model model, @ModelAttribute("user") Users user) {
		
		//세션 담기
		model.addAttribute("user", user);
		
		Long wallet_id = user.getWallet().getId();		
		model.addAttribute("wallet_id", wallet_id);
		
		//회원별 포인트 상세 내역 출력
		List<PointHistory> phDetailList =  pointHistoryService.pointHistoryList(wallet_id);
		model.addAttribute("phDetailList", phDetailList);
		
	    Integer nowPoint = pointHistoryService.updateWalletAmount(wallet_id);
	    model.addAttribute("nowPoint", nowPoint);
		
		return "mypage/pointCharge";
	}

	/**
	 * 포인트 충전시 DB에 저장 
	 */
	@PostMapping("/pointCharge")
	@ResponseBody
	public Map<String,Object> pointHistoryInsert(@ModelAttribute("user") Users user,
							@RequestParam("amount") Long amount, 
							@RequestParam("type") Integer type,
							@RequestParam("wallet_id") Long wallet_id, 
							Model model) {

		model.addAttribute("user", user);
		LocalDateTime currentTime = LocalDateTime.now();
				
	    Wallet wallet = walletService.findById(wallet_id);	
	    
	    //포인트 충전 내역 저장
		pointHistoryService.pointChargeInsert2(amount, currentTime, type, wallet);
		
		//결제 후 서버 통신 확인
	    Map<String, Object> map = new HashMap<String, Object>();

	    map.put("amount", amount);
		map.put("data", "success");
		map.put("date", currentTime);
		
		return map;
	}

}
