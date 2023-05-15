package loverduck.clover.repository;

import loverduck.clover.entity.LikedCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedCompanyRepository extends JpaRepository<LikedCompany, Long> {
}
