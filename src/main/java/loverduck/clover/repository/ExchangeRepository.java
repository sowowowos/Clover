package loverduck.clover.repository;

import loverduck.clover.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExchangeRepository extends JpaRepository<Exchange, Long>, QuerydslPredicateExecutor<Exchange> {
}
