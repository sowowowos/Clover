package loverduck.clover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import loverduck.clover.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long>, QuerydslPredicateExecutor<Funding> {
	
	/**
	 * 펀딩 전체 목록 조회 
	 */
//	@Modifying
//	void fundingList();
	
	

}
