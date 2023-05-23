package loverduck.clover.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Wallet;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, QuerydslPredicateExecutor<PointHistory> {


	
	 String INSERT_POINTHISTORY_BY_WALLET_ID =
	 "insert into point_history (id, amount, created_at, type, wallet_id) values (point_history_seq.nextval, :amount, :created_at, :type, :wallet_id)";
	 
	 @Query (nativeQuery = true, value = INSERT_POINTHISTORY_BY_WALLET_ID)
	 @Transactional	 
	 @Modifying 
	 void insertPointHistoryByWalletId(long amount,
	 LocalDateTime created_at, Integer type, Wallet wallet_id);
	

}