package loverduck.clover.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.Ordered;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.QPointHistory;
import loverduck.clover.entity.Wallet;
import loverduck.clover.repository.OrderedRepository;
import loverduck.clover.repository.PointHistoryRepository;
import loverduck.clover.repository.WalletRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryServiceImpl implements PointHistoryService {

	@Autowired
	private final PointHistoryRepository phRepository;
	
	private final OrderedRepository orderedRepository;
	
	@Autowired
	private final EntityManager entityManager;
	
    private final JPAQueryFactory qFactory;
    
    QPointHistory qpointHistory = QPointHistory.pointHistory;

    /**
     * 포인트 충전 내역 저장(+)
     * funding_id 포함 X
     */
	@Override
	@Transactional
	public void pointChargeInsert(Long amount, LocalDateTime created_at, Integer type, Wallet wallet_id) {
		phRepository.insertPointHistoryByWalletId(amount, created_at, type, wallet_id);
    }	
	
	/**
	 * 포인트 사용 내역 저장 (-)
	 * funding_id 포함 O
	 */	
	public void fundingPayInsert(Long amount, LocalDateTime created_at, Integer type, Funding funding_id, Wallet wallet_id) {
		phRepository.insertUsePointHistoryByWalletId(amount, created_at, type, funding_id, wallet_id);
		orderedRepository.save(Ordered.builder().user(wallet_id.getUser()).funding(funding_id).build());
	}
	
	/**
	 * 포인트 환전 내역 저장 (-)
	 * funding_id 포함 X
	 */	
	public void exchangeInsert(Long amount, LocalDateTime created_at, Integer type,Wallet wallet_id) {
		phRepository.insertExchangeByWalletId(amount, created_at, type, wallet_id);
	}
	
	/**
	 * 포인트 상세내역 조회(오름차순으로 정렬)
	 */
	@Override
	public List<PointHistory> pointHistoryList(Long walletId) {

		List<PointHistory> pointHistoryList = qFactory.selectFrom(qpointHistory)
			    .where(qpointHistory.wallet.id.eq(walletId))
			    .orderBy(qpointHistory.createdAt.desc())
			    .fetch();
		
		return pointHistoryList;
	}

	
	/**
	 * 포인트 충전/사용/환전/배당 내역에 따른 wallet amount 값 변경
	 */
	public Integer updateWalletAmount(Long walletId) {		

	    //포인트 type이 0(충전)과 3(배당)이면 +, 1(사용)과 2(환전)이면 -
	    //NumberExpression<Integer> amountSum = Expressions.numberTemplate(Integer.class,
	    //        "SUM(CASE WHEN {0} = 0 THEN {1} ELSE -{1} END)", qpointHistory.type, qpointHistory.amount);
	    
	    NumberExpression<Integer> amountSum = Expressions.numberTemplate(Integer.class,
	            "SUM(CASE WHEN {0} IN (0, 3) THEN {1} ELSE -{1} END)", qpointHistory.type, qpointHistory.amount);

	    // 해당 id에 해당하는 포인트 연산 결과 저장
	    Integer result = qFactory
	            .select(amountSum)
	            .from(qpointHistory)
	            .where(qpointHistory.wallet.id.eq(walletId))
	            .fetchOne();

	    // null이 아닐 경우, Wallet의 amount 업데이트
	    if (result != null) {
	        Wallet wallet = entityManager.find(Wallet.class, walletId); // 해당 id에 해당하는 Wallet 조회
	        if (wallet != null) {
	            Long currentAmount = wallet.getAmount();
	            Long newAmount = Long.valueOf(result); 
	            wallet.setAmount(newAmount); // 연산 결과 새로운 amount 값 업데이트
	            entityManager.merge(wallet); // 업데이트된 Wallet 저장
	        }
	    }

	    return result;
	}

}
