package loverduck.clover.repository;

import loverduck.clover.entity.FinanceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FinanceReportRepository extends JpaRepository<FinanceReport, Long>, QuerydslPredicateExecutor<FinanceReport> {
}
