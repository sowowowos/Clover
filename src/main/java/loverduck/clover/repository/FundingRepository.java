package loverduck.clover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import loverduck.clover.entity.Funding;
import loverduck.clover.entity.Ordered;

public interface FundingRepository extends JpaRepository<Funding, Long>, QuerydslPredicateExecutor<Funding> {
	
	/**
	 * 기업 이름 기준 검색 
	 */
	List<Funding> findByCompanyName(String keyword);
	/**
	 * 펀딩 제목 기준 검색 
	 */
	List<Funding> findByTitleContaining(String keyword);
	
	/**
	 * 해당 펀딩의 기업의 진행 중인 펀딩들 출력 
	 */
	String FIND_NOWFUNDINGS_BY_ID_QUERY = "select * from funding where company_id = :id and to_char(sysdate, 'YYYY-MM-DD') > start_date and to_char(sysdate, 'YYYY-MM-DD') < end_date";
	@Query(nativeQuery = true, value = FIND_NOWFUNDINGS_BY_ID_QUERY)
	List<Funding> findNowFundingsById(Long id);
	
	/**
	 * 해당 펀딩의 기업의 완료된 펀딩들 출력 
	 */
	String FIND_DONEFUNDINGS_BY_ID_QUERY = "select * from funding where company_id = :id and to_char(sysdate, 'YYYY-MM-DD') > end_date";
	@Query(nativeQuery = true, value = FIND_DONEFUNDINGS_BY_ID_QUERY)
	List<Funding> findDoneFundingsById(Long id);
}	
