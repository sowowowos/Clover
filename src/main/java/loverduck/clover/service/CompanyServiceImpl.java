package loverduck.clover.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRep;
	
	@Override
	public Company updateCom(String address, String detailAddress, String phone, String homepage, String email) {

		
		System.out.println(detailAddress+"detail");
		System.out.println(phone+"phone");
		System.out.println(homepage+"homepage");
		companyRep.updateCompany(address, detailAddress, phone, homepage, email);
		
		Company company = companyRep.findByCEmail(email);
		return company;
	}

}
