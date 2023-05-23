package loverduck.clover.service;

import java.time.LocalDateTime;
import java.util.List;

import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Wallet;

public interface PointHistoryService {
	
	/**
	 * 포인트 충전 내역 저장
	 */
	void pointChargeInsert(PointHistory pointHistory);
	
	/**
	 * 포인트 충전 내역 저장2
	 * funding_id, exchange_id 필요없음
	 * @param id
	 * @param amount
	 * @param created_at
	 * @param type
	 * @param wallet_id
	 */	
	void pointChargeInsert2(Long amount, LocalDateTime created_at, Integer type, Wallet wallet_id);
	/**
	 * wallet_id별 포인트 내역 상세 조회
	 */
	List<PointHistory> pointHistoryList(Long id);


	/**
	 * 포인트 충전/사용 내역에 따른 wallet amount 값 변경
	 */
	
	Integer updateWalletAmount(Long id);
	
	Integer minusWalletAmount(Long id);

}
