package loverduck.clover.service;

import java.util.List;

import loverduck.clover.entity.Funding;

public interface FundingService {
	
	/**
	 * 펀드 신청 조회 (펀딩 신청 내역 보기)
	 */
	List<Funding> historyCorp();
	
	/**
	 * 펀드 신청 ( 등록 )
	 */
	void fundSubmit(Funding funding);
	
	/**
	 * 펀딩 전체 목록 조회 
	 */
	List<Funding> fundingList();

}
