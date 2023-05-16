package loverduck.clover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 문의하기의 댓글 등록, 삭제
 */
@Controller
public class ReplyController {

	/**
	 * 댓글 등록품
	 */
	
	@RequestMapping("/replyForm")
	public String replyForm(Long bno, Model model) {
		
		return "replyForm";
	}

	/**
	 * 등록하기 : 게시물 등록 완료후 board/read.jsp으로 이동한다.
	 */
	@RequestMapping("/replyInsert")
	public String replyInsert() {
	
	
		return "replyInsert";

	}

	/**
	 * 댓글 삭제하기 :
	 */
	@RequestMapping("/replyDelete") // /{rno}/{bno}
	public String replyDelete() {  //@PathVariable Long rno, @PathVariable Long bno

		return "redirect:/";
	}

}
