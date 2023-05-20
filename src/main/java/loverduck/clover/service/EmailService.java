package loverduck.clover.service;

import loverduck.clover.entity.EmailMessage;

public interface EmailService {
	/**
	 * 메일 전송 
	 */
	 void sendMail(EmailMessage emailMessage, String category);
	 
}
