package loverduck.clover.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Users;
import loverduck.clover.entity.Wallet;
import loverduck.clover.service.ExchangeService;
import loverduck.clover.service.FundingService;
import loverduck.clover.service.PointHistoryService;
import loverduck.clover.service.WalletService;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class ExchangeCotroller {
	
	private final PointHistoryService pointHistoryService;
	
	private final WalletService walletService;
	
	private final FundingService fundingService;
	
	
	private final ExchangeService exchangeService;


	@ModelAttribute("user")
    public Users getUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }
	
    /**
     * 환전 신청시 
     * 1) exchange 테이블에 해당 값 저장
     * 2)  pointHistroy에서 해당 exchangeId와 walletId 참조해서 환전 내역 저장 후 amount 값 변경
     * 3) status default = 0(대기) --> 0(대기) 1(승인), 2(거절, 거절 이면 포인드 재충전)
     * 4) 관리자가 승인 시 status 0으로 변경, 거절시 2로 변경
     */
	
	
	 /**
     * MYPAGE - 환전 신청
     * 
     */
    @GetMapping(value="/exchangeSubmit")
    public String exchangeSubmit(@ModelAttribute("user") Users user, Model model) {

    	model.addAttribute("user", user);
    	
        return "mypage/exchangeSubmit";
    }

    /**
     * exchange, pointHistory에 저장
     */
    @PostMapping (value="/exchangeSubmit")
    public String exchangeInsert(String bank, String accountHolder,
						    		String account, long amount,
						    		Model model, @ModelAttribute("user") Users user){
    
    	model.addAttribute("user", user);
    	
    	long status = 0; //
    	long userId = user.getId();
    	String userName = user.getName();
    	Integer userType = user.getType();
    	long walletId = user.getWallet().getId();
    	
    	LocalDateTime currentTime = LocalDateTime.now();		
    	Wallet wallet = walletService.findById(walletId);
    	
    	//exchangeService.exchangeInsert();
    	
    	exchangeService.exchangeInsert(bank, accountHolder, account, amount, status, userType, wallet, currentTime);
    	
    	//포인트내역 저장
    	//pointHistroyService.exchangeSubmitInsert()
    	
	    //Long type = userType; //pointHistory에 저장할 회원 타입
	    
	    //exchange에 저장
	    //pointHistroy에 저장

	    return "mypage/pointCharge";
    	
    }
    
    

    /**
     * 마이페이지 - 환전신청내역
     */
	/*
	 * @RequestParam("userName") Long userName,
	 * 
	 * @RequestParam("userType") Long userType,
	 * 
	 * @RequestParam("walletId") Long walletId,
	 * 
	 * @RequestParam("bank") Long bank,
	 * 
	 * @RequestParam("accountHolder") Long accountHolder,
	 * 
	 * @RequestParam("account") Long account,
	 * 
	 * @RequestParam("amount") Long amount,
	 * 
	 * @RequestParam("status") Long status,
	 */
}
