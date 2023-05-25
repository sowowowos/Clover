package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.QCompany;
import loverduck.clover.entity.QFunding;
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

    public List<Funding> getFundingList() {
        return qFactory.selectFrom(QFunding.funding)
                .orderBy(QFunding.funding.endDate.desc())
                .fetch();
    }

    public Funding updateFundingStatus(Long id, Integer status) {
        return qFactory.update(QFunding.funding)
                .set(QFunding.funding.status, status)
                .where(QFunding.funding.id.eq(id))
                .execute() == 1 ? qFactory.selectFrom(QFunding.funding)
                .where(QFunding.funding.id.eq(id))
                .fetchOne() : null;
    }
}
