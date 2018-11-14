package com.javalec.spring_pjt_board.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.spring_pjt_board.dao.BDao;
import com.javalec.spring_pjt_board.dto.BDto;

public class BContentCommand implements BCommand{

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap(); //model객체에서 Map형식으로 map을 가져옴.
		
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		String bId = req.getParameter("bId");
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		model.addAttribute("content_view", dto);
	}

}
