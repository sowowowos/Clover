package loverduck.clover.repository;

import loverduck.clover.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KeywordRepository extends JpaRepository<Keyword, Long>, QuerydslPredicateExecutor<Keyword> {
}
