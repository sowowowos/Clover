package loverduck.clover.service;

import java.time.LocalDateTime;
import java.util.List;

import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.Wallet;

public interface ExchangeService {
	
	/**
	 * 포인트 환전 신청
	 */
	void exchangeInsert(String bank, String accountHolder, 
			String account, long amount, long status, Integer type, 
			Wallet wallet_id, LocalDateTime created_at);

	
	/**
	 * 환전 신청 내역 리스트
	 */
	List<Exchange> exchageSubmitList(Long user_id);
	


	

}
