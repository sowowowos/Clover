package loverduck.clover.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.entity.EmailMessage;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    
    private final UsersService usersService;
    
    public void sendMail(EmailMessage emailMessage, String category) {
    	
    	// 수신자, 제목, 내용 등을 설정 
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        if (category.equals("dividend")) {
        	
        }
        
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            
            // 테스트 
            //mimeMessageHelper.setTo("ruisya330@gmail.com");
            //mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            
            mimeMessageHelper.setSubject("저겨 ");
            mimeMessageHelper.setText(setContext(category), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Success");

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
            	
    }
    
    
    // thymeleaf를 통한 html 적용
    public String setContext(String category) {
        Context context = new Context();
        return templateEngine.process(category, context);
    }

}
