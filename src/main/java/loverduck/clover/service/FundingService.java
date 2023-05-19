package loverduck.clover.service;

import java.util.List;

import loverduck.clover.entity.Funding;
import loverduck.clover.entity.FundingReply;

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
	
	/**
	 * 펀딩 상세 조회 
	 */
	Funding fundingDetail(Long id);
	
	/**
	 * 기업 이름 기준 진행 중인 펀드 목록 조회 
	 */
	List<Funding> findByCompanyName(String name);
	
	/**
	 * 펀딩 상세 페이지 댓글 작성 
	 */
	void fundingComment(FundingReply fundingReply);
	
	/**
	 * 펀딩 상세 페이지 댓글 목록 출력 
	 */
	List<FundingReply> commentList(Long id);

	
}
