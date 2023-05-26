package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public List<Exchange> getExchangeList() {
        return qFactory.selectFrom(QExchange.exchange)
                .orderBy(QExchange.exchange.createdAt.desc())
                .fetch();
    }

    public Exchange updateExchangeType(Long id, Integer status) {
        return qFactory.update(QExchange.exchange)
                .set(QExchange.exchange.type, status)
                .where(QExchange.exchange.id.eq(id))
                .execute() == 1 ? qFactory.selectFrom(QExchange.exchange)
                .where(QExchange.exchange.id.eq(id))
                .fetchOne() : null;
    }
}
