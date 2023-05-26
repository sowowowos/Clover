package loverduck.clover.repository;

import loverduck.clover.entity.Ordered;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderedRepository extends JpaRepository<Ordered, Long>, QuerydslPredicateExecutor<Ordered> {

}
