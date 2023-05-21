package loverduck.clover.service;

import loverduck.clover.entity.Wallet;

public interface WalletService {
	Wallet findById(Long walletId);
}
