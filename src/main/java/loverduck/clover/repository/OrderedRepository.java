package loverduck.clover.repository;

import loverduck.clover.entity.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedRepository extends JpaRepository<Ordered, Long> {
}
