package org.zerock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class MemberTests {
	
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
	@Test
	public void insertUsers() {
		String sql = "insert into tbl_member(userid,userpw,username) values (?,?,?)";
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try(Connection conn = con;
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			for(int i = 0; i< 100;i++) {
				
				
					
					pstmt.setString(2, pwencoder.encode("pw"+ i));
					if(i < 80) {
						pstmt.setString(1, "user" + i);
						pstmt.setString(3, "일반사용자" + i);
					} else if(i < 90) {
						pstmt.setString(1, "manager" + i);
						pstmt.setString(3, "운영자" + i);
					} else {
						pstmt.setString(1, "admin" + i);
						pstmt.setString(3, "관리자" + i);
					}
					
					pstmt.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertAuths() {
		String sql = "insert into tbl_member_auth (userid,auth) values (?,?)";
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try(Connection conn = con;
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			for(int i = 0; i< 100;i++) {
					if(i < 80) {
						pstmt.setString(1, "user" + i);
						pstmt.setString(2, "ROLE_USER");
						pstmt.executeUpdate();
					} else if(i < 90) {
						pstmt.setString(1, "manager" + i);
						pstmt.setString(2, "ROLE_USER");
						pstmt.executeUpdate();
						pstmt.setString(1, "manager" + i);
						pstmt.setString(2, "ROLE_MANAGER");
						pstmt.executeUpdate();
					} else {
						pstmt.setString(1, "admin" + i);
						pstmt.setString(2, "ROLE_USER");
						pstmt.executeUpdate();
						pstmt.setString(1, "admin" + i);
						pstmt.setString(2, "ROLE_MANAGER");
						pstmt.executeUpdate();
						pstmt.setString(1, "admin" + i);
						pstmt.setString(2, "ROLE_ADMIN");
						pstmt.executeUpdate();
					}		
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead() {
		MemberVO vo = mapper.read("admin90");
		log.info(vo);
	}
		
}
