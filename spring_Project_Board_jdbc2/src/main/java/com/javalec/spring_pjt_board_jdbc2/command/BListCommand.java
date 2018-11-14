package com.javalec.spring_pjt_board_jdbc2.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.spring_pjt_board_jdbc2.dao.BDao;
import com.javalec.spring_pjt_board_jdbc2.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		
		model.addAttribute("list", dtos);
		
	}

}
