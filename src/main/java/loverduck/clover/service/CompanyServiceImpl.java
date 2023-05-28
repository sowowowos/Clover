package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.QCompanyMapData;
import loverduck.clover.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRep;
    private final JPAQueryFactory qFactory;

    @Override
    public Company updateCom(String address, String detailAddress, String phone, String homepage, String email) {


//		System.out.println(detailAddress+"detail");
//		System.out.println(phone+"phone");
//		System.out.println(homepage+"homepage");
        companyRep.updateCompany(address, detailAddress, phone, homepage, email);

        Company company = companyRep.findByCEmail(email);
        return company;
    }

    @Override
    public Company updateCompanyType(Long id, Integer status) {
        Company company = companyRep.findById(id).orElse(null);
        if (company == null) {
            return null;
        }
        company.setType(status);
        return company;
    }

    @Override
    public Company getMapCompany(Long id) {
        return qFactory.selectFrom(QCompanyMapData.companyMapData)
                .where(QCompanyMapData.companyMapData.company.id.eq(id))
                .select(QCompanyMapData.companyMapData.company)
                .fetchOne();
    }
}
