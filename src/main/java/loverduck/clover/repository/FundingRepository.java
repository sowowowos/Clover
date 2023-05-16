package loverduck.clover.repository;

import loverduck.clover.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FundingRepository extends JpaRepository<Funding, Long>, QuerydslPredicateExecutor<Funding> {
}
