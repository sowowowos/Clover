package loverduck.clover.repository;

import loverduck.clover.entity.Company;
import loverduck.clover.entity.CrawlingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CrawlingDataRepository extends JpaRepository<CrawlingData, Long>, QuerydslPredicateExecutor<CrawlingData> {
}
