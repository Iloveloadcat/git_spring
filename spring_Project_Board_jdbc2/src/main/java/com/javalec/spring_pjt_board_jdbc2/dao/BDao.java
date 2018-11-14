package com.javalec.spring_pjt_board_jdbc2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.javalec.spring_pjt_board_jdbc2.dto.BDto;
import com.javalec.spring_pjt_board_jdbc2.util.Constant;

public class BDao {
	
	DataSource dataSource;
	
	JdbcTemplate template = null;

	public BDao() {
		
		template = Constant.template;
	}
	
	/**
	 * list
	 */
	public ArrayList<BDto> list() {
		
		String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
					        + "  FROM MVC_BOARD"
					        + " ORDER BY bGroup desc, bStep asc";
		
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
	}
	
	/**
	 * write
	 */
	public void write(final String bName, final String bTitle, final String bContent) {
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String query = "INSERT INTO MVC_BOARD (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
				          +"VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, MVC_BOARD_SEQ.CURRVAL, 0, 0)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				return pstmt;
			}
		});
	}
	
	/**
	 * contentView
	 */
	public BDto contentView(String strId) {
		
		//조회수 증가
		upHit(strId);
		
		String query = "SELECT * FROM MVC_BOARD WHERE bId = " + strId;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
	}
	
	
	public void modify(final String bId, final String bName, final String bTitle, final String bContent) {
		String query = " UPDATE MVC_BOARD"
				+ "   SET bName  		= ?"
				+ "        ,bTitle    	= ?"
				+ "        ,bContent 	= ?"
				+ "WHERE bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));
			}
		});

	}
	
	public void  delete(final String bId) {
		
		String query =    " DELETE"
				+ "   FROM MVC_BOARD"
				+ " WHERE bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bId);
				
			}
		});
	}
	
	public BDto reply_View(String strId) {
		
		String query = "SELECT * FROM MVC_BOARD "
			      + " WHERE bId = " + strId;
		
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
	}
	
	public void reply(String bId, final String bName, final String bTitle, final String bContent, final String bGroup,final String bStep,final  String bIndent) {
		
		replyShape(bGroup, bStep);
		
		String query = "INSERT INTO MVC_BOARD (bId, bName, bTitle, bContent, bGroup, bStep, bIndent)" 
				 + " VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bGroup));
				ps.setInt(5, Integer.parseInt(bStep) + 1) ;
				ps.setInt(6, Integer.parseInt(bIndent) + 1);
			}
		});
		
	}
	
	private void replyShape(final String strGroup, final String strStep) {
		
		String query = "UPDATE MVC_BOARD "
				+"       SET bStep = bStep + 1"
				+ "  WHERE bGroup = ? AND bStep > ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(strGroup));
				ps.setInt(2, Integer.parseInt(strStep));
			}
		});
		

	}
	
	
	private void upHit(final String bId) {
		
		String query = "UPDATE MVC_BOARD "
			      +"      SET bHit = bHit + 1"
			      + " WHERE bId = ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));
				
			}
		});
	}
}
