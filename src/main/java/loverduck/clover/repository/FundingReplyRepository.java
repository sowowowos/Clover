package loverduck.clover.repository;

import loverduck.clover.entity.FundingReply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface FundingReplyRepository extends JpaRepository<FundingReply, Long>, QuerydslPredicateExecutor<FundingReply> {
	
	String FIND_BY_FUNDING_ID_QUERY = "select * from funding_reply where funding_id = :id";
	@Query(nativeQuery = true, value = FIND_BY_FUNDING_ID_QUERY)
	List<FundingReply> findByFundingId(@Param("id") Long id);
}
