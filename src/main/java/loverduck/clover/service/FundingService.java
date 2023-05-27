package loverduck.clover.service;

import java.util.List;

import loverduck.clover.entity.Company;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.FundingReply;
import loverduck.clover.entity.LikedFunding;
import loverduck.clover.entity.Users;
import loverduck.clover.entity.Wallet;

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
	 * 펀딩 아이디 조회
	 */
	Funding findById(Long id);
	
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

	/**
	 * 펀딩 제목 기준 검색 
	 */
	List<Funding> searchFundingByTitle(String keyword);
	
	/**
	 * 기업 이름 기준 검색 
	 */
	List<Funding> searchFundingByCompany(String keyword);
	
	/**
	 * 펀딩 내용 기준 검색 
	 */
	List<Funding> searchFundingByContent(String keyword);
	
	/**
	 * 현재 펀딩의 기업의 진행 중인 펀딩 목록 출력 
	 */
	List<Funding> findNowFundingsById(Long id);
	
	/**
	 * 현재 펀딩의 기업의 완료된 펀딩 목록 출력 
	 */
	List<Funding> findDoneFundingsById(Long id);

	
	/**
	 * 기업이 펀드 신청한 목록 보기
	 */
	List<Funding> fundingSubmitList(Long company_id);
	
	/**
	 * 관심 펀드 추가 (펀딩 좋아요)
	 */
	boolean addLike(Long funding_id, Long user_id);
	
	/**
	 * 관심 펀드 삭제 (펀딩 좋아요 취소)
	 */
	boolean removeLike(Funding funding, Users user);
	
}
