package loverduck.clover.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.repository.PointHistoryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryServiceImpl implements PointHistoryService {

	private final PointHistoryRepository pointHistoryRepository;

	@Override
	public PointHistory pointHistoryDetail(Long id) {
		PointHistory pointHistory = pointHistoryRepository.findById(id).orElse(null);
		return pointHistory;
	}

}
