package loverduck.clover.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import loverduck.clover.entity.LikedFunding;


public interface LikedFundingRepository extends JpaRepository<LikedFunding, Long> {

	
	String FIND_BY_FUNDING_ID_AND_USER_ID_QUERY = "select * from liked_funding where funding_id = :funding_id and user_id = :user_id";
	@Query(nativeQuery = true, value = FIND_BY_FUNDING_ID_AND_USER_ID_QUERY)
	LikedFunding findByFundingAndUser(@Param("funding_id") Long funding_id, @Param("user_id") Long user_id);

}
