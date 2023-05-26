package loverduck.clover.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.QExchange;
import loverduck.clover.entity.Wallet;
import loverduck.clover.repository.ExchangeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
	
	private final ExchangeRepository exRepository;

	@Autowired
	private final EntityManager entityManager;
	
	private final JPAQueryFactory qFactory;
	
	QExchange qexchage = QExchange.exchange;
	
	/**
	 * 포인트 환전 신청
	 */
	@Override
	public void exchangeInsert(String bank, String accountHolder, String account, long amount, long status,
			Integer type, Wallet wallet_id, LocalDateTime created_at) {
		exRepository.insertExchangeByWalletId(bank, accountHolder, account, amount, status, type, wallet_id, created_at);
	}

	/**
	 * 환전 신청 내역 리스트
	 */
	@Override
	public List<Exchange> exchageSubmitList(Long company_id) {

		List<Exchange> exchageList = qFactory.selectFrom(qexchage)
				.where(qexchage.company.id.eq(company_id))
				.orderBy(qexchage.createdAt.desc())
				.fetch();
		return exchageList;
	}

}
