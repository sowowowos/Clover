package loverduck.clover.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Wallet;
import loverduck.clover.repository.PointHistoryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryServiceImpl implements PointHistoryService {

	private final PointHistoryRepository phRepository;

	
	@Override
	@Transactional
	public void pointChargeInsert(PointHistory pointHistory) {

		phRepository.save(pointHistory);
	}

	@Override
	public List<PointHistory> pointHistoryList(Long id) {
		List<PointHistory> pointHistoryListById = phRepository.findAll();
		return pointHistoryListById;
	}

	@Override
	public PointHistory pointHistoryDetail(Long id) {
		PointHistory pointHistory = phRepository.findById(id).orElse(null);
		return pointHistory;
	}



}
