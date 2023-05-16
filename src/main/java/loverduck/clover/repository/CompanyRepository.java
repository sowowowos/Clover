package loverduck.clover.repository;

import loverduck.clover.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company> {
}
