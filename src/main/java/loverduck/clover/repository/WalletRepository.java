package loverduck.clover.repository;

import loverduck.clover.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WalletRepository extends JpaRepository<Wallet, Long>, QuerydslPredicateExecutor<Wallet> {
}
