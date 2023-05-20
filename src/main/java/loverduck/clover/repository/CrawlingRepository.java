package loverduck.clover.repository;

import loverduck.clover.entity.CrawlingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CrawlingRepository extends JpaRepository<CrawlingData, Long>, QuerydslPredicateExecutor<CrawlingData> {
    CrawlingData findByName(String name);
}
