package com.javalec.spring_pjt_board_jdbc2.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.javalec.spring_pjt_board_jdbc2.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		
		
		String bName = req.getParameter("bName");
		String bTitle = req.getParameter("bTitle");
		String bContent = req.getParameter("bContent");
		
		BDao dao = new BDao();
		dao.write(bName, bTitle, bContent); 
		
	}

}
