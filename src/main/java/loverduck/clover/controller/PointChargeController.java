package loverduck.clover.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class PointChargeController {
	@Autowired
	PointHistoryService pointHistoryService;
	
	@Autowired
	WalletService walletService;

	// 포인트 상세 내역

	@GetMapping(value = "/pointCharge")
	public String pointCharge() {

		return "mypage/pointCharge";
	}

	
	@PostMapping("/pointCharge")
	@ResponseBody
	public Map<String,Object> pointHistoryInsert(@RequestParam("amount") Long amount, 
			@RequestParam("type") Integer type,
			@RequestParam("wallet_id") Long walletid, HttpSession session, 
			@PathVariable Long wallet_id, Model model) {

		Long id = 1L;
		Users u = (Users)session.getAttribute("user");
		LocalDateTime currentTime = LocalDateTime.now();
		
	    Wallet wallet = walletService.findById(walletid);
	    model.addAttribute("wallet", wallet);

	    //포인트 충전 내역 저장
		PointHistory pointHistory = new PointHistory(id, amount, type, currentTime, wallet, null, null);
	    pointHistoryService.pointChargeInsert(pointHistory);
	    
	    //포인트 내역 상세 목록 출력
	    PointHistory phDetail = pointHistoryService.pointHistoryDetail(wallet_id);
	    model.addAttribute("phDetail",phDetail); 
		
		//결제 후 서버 통신 확인
	    Map<String, Object> map = new HashMap<String, Object>();

	    map.put("amount", 100);
		map.put("data", "success");
		map.put("date", currentTime);
		//pointHistoryService.pointChargeInsert(new PointHistory(amount, null, currentTime));
		
		return map;
	}

//	@RequestMapping("/pointCharge/{wallet_id}")
//	public String pointHistoryDetail(@PathVariable Long id, Model model) {
//		
//		PointHistory pointHistory = pointHistoryService.pointHistoryDetail(id);
//		model.addAttribute("ph",pointHistory);
//		
//		return "pointCharge";
//	}
}
