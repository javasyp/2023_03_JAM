package com.KoreaIT.example.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConTest {
	public static void main(String[] args) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 127.0.0.1 - 로컬호스트 번호
			// 3306 - 포트번호
			// JAM = DB
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			// "root" - 사용자 이름
			// "" - 비밀번호 (설정 안 함)
			System.out.println("연결 성공!");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}