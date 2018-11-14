package com.javalec.spring_pjt_board_jdbc2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.spring_pjt_board_jdbc2.command.BCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BContentCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BDeleteCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BListCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BModifyCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BReplyCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BReplyViewCommand;
import com.javalec.spring_pjt_board_jdbc2.command.BWriteCommand;
import com.javalec.spring_pjt_board_jdbc2.util.Constant;

@Controller
public class BController {

	BCommand command;
	
	public JdbcTemplate template;
	
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list@@@");
		
		command = new BListCommand();
		command.execute(model);
		
		return "list";
	}
	
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view@@@");
		
		return "write_view";
	}
	
	
	@RequestMapping("/write")
	public String write(HttpServletRequest req, Model model) {
		System.out.println("write@@@");
		
		model.addAttribute("request", req);
		
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	
	@RequestMapping("content_view")
	public String content_view(HttpServletRequest req, Model model) {
		System.out.println("write@@@");
		
		model.addAttribute("request", req);
		command = new BContentCommand();
		command.execute(model);
		
		return "content_view";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/modify")
	public String modify(HttpServletRequest req, Model model) {
		System.out.println("modify@@@");
		
		model.addAttribute("request", req);
		
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest req, Model model) {
		System.out.println("reply_view@@@");
		
		model.addAttribute("request", req);
		
		command = new BReplyViewCommand();
		command.execute(model);
		
		return "reply_view";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest req, Model model) {
		System.out.println("reply@@@");
		
		model.addAttribute("request", req);
		
		command = new BReplyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req, Model model) {
		System.out.println("delete@@@");
		
		model.addAttribute("request", req);
		
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
}
