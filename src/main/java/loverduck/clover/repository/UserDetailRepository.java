package loverduck.clover.repository;

import loverduck.clover.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, QuerydslPredicateExecutor<UserDetail> {
}
