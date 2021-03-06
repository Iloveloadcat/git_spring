package com.javalec.spring_pjt_board_jdbc2.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.spring_pjt_board_jdbc2.dao.BDao;
import com.javalec.spring_pjt_board_jdbc2.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		
		String bId = req.getParameter("bId");
		
		BDao dao = new BDao();
		BDto dto = dao.reply_View(bId);
		
		model.addAttribute("reply_view", dto);
		
	}

}
