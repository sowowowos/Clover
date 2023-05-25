package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.QCompany;
import loverduck.clover.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final JPAQueryFactory qFactory;

    public List<Company> getCompanyList() {
        return qFactory.selectFrom(QCompany.company)
                .orderBy(QCompany.company.createdAt.desc())
                .fetch();
    }
}
