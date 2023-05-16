package loverduck.clover.repository;

import loverduck.clover.entity.FundingReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FundingReplyRepository extends JpaRepository<FundingReply, Long>, QuerydslPredicateExecutor<FundingReply> {
}
