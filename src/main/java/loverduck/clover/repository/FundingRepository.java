package loverduck.clover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import loverduck.clover.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long>, QuerydslPredicateExecutor<Funding> {
	
	/**
	 * 기업 이름 기준 검색 
	 */
	List<Funding> findByCompanyName(String keyword);
	/**
	 * 펀딩 제목 기준 검색 
	 */
	List<Funding> findByTitleContaining(String keyword);
}
