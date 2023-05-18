package loverduck.clover.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
public class PaymentsController {

	@GetMapping(value="/pointCharge")
	public String iamport(@RequestParam("amount") Long amount) {
		System.out.println(amount);
		
		return "redirect:/mypage/pointChargeFin";
	}
}
