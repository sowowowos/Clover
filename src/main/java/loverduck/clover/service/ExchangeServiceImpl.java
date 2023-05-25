package loverduck.clover.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Wallet;
import loverduck.clover.repository.ExchangeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
	
	private final ExchangeRepository exRepository;

	/**
	 * 포인트 환전 신청
	 */
	@Override
	public void exchangeInsert(String bank, String accountHolder, String account, long amount, long status,
			Integer type, Wallet wallet_id, LocalDateTime created_at) {
		exRepository.insertExchangeByWalletId(bank, accountHolder, account, amount, status, type, wallet_id, created_at);
	}

}
