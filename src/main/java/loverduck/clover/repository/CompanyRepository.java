package loverduck.clover.repository;

import loverduck.clover.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company> {
	
	@Query("select c from Company c where c.email = :email")
	Company findByCEmail(String email);
	
	/**
	 * 수정하기
	 * @param address
	 * @param detailAddress
	 * @param phone
	 * @param homepage
	 */
    @Transactional
    @Modifying
    @Query("update Company c set c.address = :address, c.detailAddress = :detailAddress, c.phone = :phone, c.homepage = :homepage where c.email = :email")
    void updateCompany(String address,String detailAddress ,String phone, String homepage, String email);
    
}
