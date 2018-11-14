package com.javalec.spring_pjt_board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javalec.spring_pjt_board.dto.BDto;

public class BDao {
	
	DataSource dataSource;

	public BDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * list
	 */
	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
					          + "  FROM MVC_BOARD"
					          + " ORDER BY bGroup desc, bStep asc";
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				int bId				= rs.getInt("bId");
				String bName		= rs.getString("bName");
				String bTitle		= rs.getString("bTitle");
				String bContent	= rs.getString("bContent");
				Timestamp bDate	= rs.getTimestamp("bDate");
				int bHit				= rs.getInt("bHit");
				int bGroup			= rs.getInt("bGroup");
				int bStep			= rs.getInt("bStep");
				int bIndent			= rs.getInt("bIndent");
				
				BDto bdto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(bdto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
			}
		}
		
		return dtos;
	}
	
	/**
	 * write
	 */
	public void write(String bName, String bTitle, String bContent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "INSERT INTO MVC_BOARD (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
					          +"VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, MVC_BOARD_SEQ.CURRVAL, 0, 0)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			 try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * contentView
	 */
	public BDto contentView(String strId) {
		
		//조회수 증가
		upHit(strId);
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			String query = "SELECT * FROM MVC_BOARD WHERE bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strId));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
			}
		}
		
		
		return dto;
	}
	
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = dataSource.getConnection();
			String query = " UPDATE MVC_BOARD"
								+ "   SET bName  		= ?"
								+ "        ,bTitle    	= ?"
								+ "        ,bContent 	= ?"
								+ "WHERE bId = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void  delete(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query =    " DELETE"
								+ "   FROM MVC_BOARD"
								+ " WHERE bId = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public BDto reply_View(String strId) {
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			String query = "SELECT * FROM MVC_BOARD "
						      + " WHERE bId = ?";
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strId));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int bId 				= rs.getInt("bId");
				String bName 		= rs.getString("bName"); 
				String bTitle 		= rs.getString("bTitle"); 
				String bContent 	= rs.getString("bContent"); 
				Timestamp bDate	= rs.getTimestamp("bDate");
				int bHit				= rs.getInt("bHit");
				int bGroup			= rs.getInt("bGroup");
				int bStep			= rs.getInt("bStep");
				int bIndent			= rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 try {
				 if (rs != null) {
					 rs.close();
				 }
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto;
	}
	
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		
		replyShape(bGroup, bStep);
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "INSERT INTO MVC_BOARD (bId, bName, bTitle, bContent, bGroup, bStep, bIndent)" 
							 + " VALUES (MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			 try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private void replyShape(String strGroup, String strStep) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "UPDATE MVC_BOARD "
							+"       SET bStep = bStep + 1"
							+ "  WHERE bGroup = ? AND bStep > ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	private void upHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "UPDATE MVC_BOARD "
						      +"      SET bHit = bHit + 1"
						      + " WHERE bId = ?";
	
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			 try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
