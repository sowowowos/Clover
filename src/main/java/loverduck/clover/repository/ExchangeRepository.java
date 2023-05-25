package loverduck.clover.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import loverduck.clover.entity.Exchange;
import loverduck.clover.entity.Wallet;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long>, QuerydslPredicateExecutor<Exchange> {


	 String INSERT_EXCHANGE_BY_WALLET_ID =
	 "insert into exchange (id, bank, account_holder, account, amount, status, type, wallet_id, created_at) "
	 + "values (exchange_seq.nextval, :bank, :accountHolder, :account, :amount, :status, :type, :wallet_id, :created_at)";

	 
	 @Query(nativeQuery = true, value = INSERT_EXCHANGE_BY_WALLET_ID)
	 @Transactional	 
	 @Modifying 
	 void insertExchangeByWalletId(String bank, String accountHolder, String account, 
			 long amount, long status, Integer type, Wallet wallet_id, LocalDateTime created_at);

}
