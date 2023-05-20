package loverduck.clover.service;

import loverduck.clover.entity.PointHistory;

public interface PointHistoryService {
	
	/*
	 * 포인트 사용 상세 내역 조회
	 */
	PointHistory pointHistoryDetail(Long id);

}
