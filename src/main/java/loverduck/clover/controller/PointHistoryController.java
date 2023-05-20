package loverduck.clover.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import loverduck.clover.entity.PointHistory;
import loverduck.clover.service.PointHistoryService;

@RestController
@RequestMapping("/mypage")
public class PointHistoryController {
	@Autowired
	PointHistoryService pointHistoryService;

	// 포인트 상세 내역
	@RequestMapping("/pointHistory/{id}")
	public String pointHistoryDetail(@PathVariable Long id, Model model) {
		PointHistory pointHistory = pointHistoryService.pointHistoryDetail(id);
		model.addAttribute("pointHistory", pointHistory);

		return "/pointHistory";
	}

}
