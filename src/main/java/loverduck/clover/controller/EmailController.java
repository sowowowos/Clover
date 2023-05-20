package loverduck.clover.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.EmailMessage;
import loverduck.clover.service.EmailService;
import loverduck.clover.service.FundingService;

@Controller
@RequiredArgsConstructor
public class EmailController {

	@Autowired
	private final EmailService emailService;
	
	/**
	 * category => 배당금(emailDividend) / 문의 답변(emailReply)
	 */
	@RequestMapping("/send")
	public void sendDividendMaiil(EmailMessage emailMessage, @RequestParam("category") String category) {
		emailService.sendMail(emailMessage, category);
	}
}
