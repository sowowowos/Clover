package loverduck.clover.service;

import java.util.List;

import loverduck.clover.entity.PointHistory;

public interface PointHistoryService {
	
	/**
	 * 포인트 충전 내역 저장
	 */
	void pointChargeInsert(PointHistory pointHistory);
	

	List<PointHistory> pointHistoryList(Long id);
	
	/**
	 * 포인트 내역 상세 조회
	 */
	PointHistory pointHistoryDetail(Long id);

}
