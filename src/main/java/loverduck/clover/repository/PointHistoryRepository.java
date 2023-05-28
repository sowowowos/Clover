package loverduck.clover.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.Ordered;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Users;
import loverduck.clover.entity.Wallet;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, QuerydslPredicateExecutor<PointHistory> {
	
	/* 포인트 충전 내역 저장 */
	 String INSERT_POINTHISTORY_BY_WALLET_ID =
	 "insert into point_history (id, amount, created_at, type, wallet_id) values (point_history_seq.nextval, :amount, :created_at, :type, :wallet_id)";
	 
	 @Query (nativeQuery = true, value = INSERT_POINTHISTORY_BY_WALLET_ID)
	 @Transactional	 
	 @Modifying 
	 void insertPointHistoryByWalletId(long amount,
	 LocalDateTime created_at, Integer type, Wallet wallet_id);
	 
	 /* 포인트 사용 내역 저장 */
	 //기업에 펀딩할 시 funding_id도 insert, type=1(사용)	
	 String INSERT_USE_POINTHISTORY_BY_WALLET_ID =
	 "insert into point_history (id, amount, created_at, type, funding_id, wallet_id) values (point_history_seq.nextval, :amount, :created_at, :type, :funding_id, :wallet_id)";
	 
	 @Query (nativeQuery = true, value = INSERT_USE_POINTHISTORY_BY_WALLET_ID)
	 @Transactional	 
	 @Modifying 
	 void insertUsePointHistoryByWalletId(long amount,
	 LocalDateTime created_at, Integer type, Funding funding_id, Wallet wallet_id); 
	 
	 
	 /**
	  * 사용자의 배당 내역 조회 (정산)
	  */
	 String FIND_ALLOCATIONS_BY_USER_ID = "select * from point_history where wallet_id = :wallet_id and type = 3";
	 @Query(nativeQuery = true, value = FIND_ALLOCATIONS_BY_USER_ID)
	 List<PointHistory> findAllocationsByUserId (Long wallet_id);

  /* 포인트 환전 내역 저장 */
	 String INSERT_EXCHANGE_POINTHISTORY_BY_WALLET_ID =
			 "insert into point_history (id, amount, created_at, type, wallet_id) values (point_history_seq.nextval, :amount, :created_at, :type, :wallet_id)";

	 	 @Query (nativeQuery = true, value = INSERT_EXCHANGE_POINTHISTORY_BY_WALLET_ID)
	 @Transactional	 
	 @Modifying 
	 void insertExchangeByWalletId(long amount, LocalDateTime created_at, Integer type, Wallet wallet_id);

}