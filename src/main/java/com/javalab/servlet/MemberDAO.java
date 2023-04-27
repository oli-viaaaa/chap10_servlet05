package com.javalab.servlet;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;


/**
 * DAO(Data Access Object) 
 * 	- 데이터베이스 관련 작업 전담 클래스
 * 	- JDBC 드라이버 로딩/커넥션 생성/쿼리문 작성/쿼리문 실행
 */

public class MemberDAO {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl"; //"jdbc:oracle:thin:@127.0.0.1:1521:orcl"; 
	private static final String user = "company";
	private static final String pwd = "1234";
	
	private Connection con;
	private PreparedStatement pstmt;
	
	// 생성자
	public MemberDAO() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			con = DriverManager.getConnection(url,user,pwd);
			System.out.println("Connection 성공");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	private void connectDB() {
//		try {
//			Class.forName(driver);
//			System.out.println("Oracle 드라이버 로딩 성공");
//			con = DriverManager.getConnection(url,user,pwd);
//			System.out.println("Connection 성공");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	// 회원 목록 조회 메소드
	public ArrayList<MemberVO> listMembers(){
		ArrayList<MemberVO> list = new ArrayList<>();
		try {
			// connectDB(); // 데이터 베이스 커넥션 생성 메소드 호출
			
			String query = " select * from member";
			System.out.println("prepareStatememt" + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo);
				
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 회원 한명 조회
	public MemberVO getMember(String id) {
		MemberVO member = null;
		try {
			String query = " select m.id, m.name, m.email, m.joindate";
				query += " from member m";
				query += " where m.id=?";
				System.out.println("prepareStatememt : " +query);
				
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, id);
				ResultSet  rs = pstmt.executeQuery();
				
				if (rs.next()) {
					member = new MemberVO();
					member.setId(rs.getString("id"));
					member.setName(rs.getString("name"));
					member.setEmail(rs.getString("email"));
					member.setJoinDate(rs.getDate("joindate"));
				}
				rs.close();
				pstmt.close();
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
	
}
