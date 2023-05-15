package loverduck.clover.repository;

import loverduck.clover.entity.FinanceReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceReportRepository extends JpaRepository<FinanceReport, Long> {
}
