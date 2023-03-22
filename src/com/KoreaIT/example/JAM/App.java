package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;
import com.KoreaIT.example.JAM.util.util;

public class App {
	public void start() {
		
		System.out.println("=== 프로그램 시작 ===");
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();
			
			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("예외 : 클래스가 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			
			try {
				conn = DriverManager.getConnection(url, "root", "");
				
				int actionResult = doAction(conn, sc, cmd);
				
				if (actionResult == -1) {
					System.out.println("프로그램을 종료합니다.");
					break;
				}
			} catch (SQLException e) {
				System.out.println("에러1 : " + e);
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
		
	private int doAction(Connection conn, Scanner sc, String cmd) {
		
		if (cmd.equals("exit")) {
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}
		
		// 회원가입
		if (cmd.equals("member join")) {
			System.out.println("--- 회원가입 ---");
			
			String loginId = null;
			String loginPw = null;
			String loginPwConfirm = null;
			String name = null;
			
			while (true) {				
				System.out.print("아이디 : ");
				loginId = sc.nextLine().trim();		// 공백 데이터 위험 trim() 추가
				
				// "아이디" 필수 입력
				if (loginId.length() == 0) {
					System.out.println("아이디를 입력해 주세요.");
					continue;
				}
				break;
			}
			
			while (true) {
				System.out.print("비밀번호 : ");
				loginPw = sc.nextLine().trim();
				
				// "비밀번호" 필수 입력
				if (loginPw.length() == 0) {
					System.out.println("비밀번호를 입력해 주세요.");
					continue;
				}
				
				boolean loginPwCheck = true;	// 비밀번호가 일치한다고 가정
				
				while (true) {
					System.out.print("비밀번호 확인 : ");
					loginPwConfirm = sc.nextLine().trim();
					
					// "비밀번호 확인" 필수 입력
					if (loginPwConfirm.length() == 0) {
						System.out.println("비밀번호 확인을 입력해 주세요.");
						continue;
					}
					
					// 비밀번호 불일치
					if (loginPw.equals(loginPwConfirm) == false) {
						System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
						loginPwCheck = false;
						break;
					}
					break;		// 다시 "비밀번호"부터 입력
				}
				
				if (loginPwCheck) {
					break;		// 일치하면(true) 반복문 종료
				}
			}
			
			while (true) {				
				System.out.print("이름 : ");
				name = sc.nextLine().trim();
				
				// 필수 입력
				if (name.length() == 0) {
					System.out.println("이름을 입력해 주세요.");
					continue;
				}
				break;
			}
			
			SecSql sql = new SecSql();
			
			sql.append("INSERT INTO `member`");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("loginId = ?,", loginId);
			sql.append("loginPw = ?,", loginPw);
			sql.append("`name` = ?", name);
			
			int id = DBUtil.insert(conn, sql);
			
			System.out.println(id + "번 회원님, 가입되었습니다.");
			
		// 입력
		} else if (cmd.equals("article write")) {
			System.out.println("--- 게시물 작성 ---");
			
			System.out.print("제목 : ");
			String title = sc.nextLine();
			
			System.out.print("내용 : ");
			String body = sc.nextLine();
			
			SecSql sql = new SecSql();
			
			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("title = ?,", title);
			sql.append("`body` = ?", body);
			
			int id = DBUtil.insert(conn, sql);	// 입력 실행
			
			System.out.println(id + "번 글이 생성되었습니다.");
			
		// 목록
		} else if (cmd.equals("article list")) {
			System.out.println("--- 게시물 목록 ---");
			
			List<Article> articles = new ArrayList<>();		// 출력할 리스트 생성
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");
			
			// 목록 가져올 때(selectRows 실행 시) 반환 타입이 List
			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
			
			for (Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
			}
			
			if (articles.size() == 0) {
				System.out.println("게시글이 없습니다.");
				return 0;
			}

			System.out.println("번호  /  제목");
			
			for (Article article : articles) {
				System.out.printf("%d   /   %s\n", article.id, article.title);
			}
			
		// 상세보기
		}  else if (cmd.startsWith("article detail ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			System.out.println("--- 게시물 상세보기 ---");
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			// list는 복수(selectRows) 상세보기는 단수(selectRow)
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			
			// 해당 번호 글 있는지 확인
			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 존재하지 않습니다.");
				return 0;
			}
			
			Article article = new Article(articleMap);
			
			System.out.println("번호 : " + article.id);
			System.out.println("작성날짜 : " + util.getNowDateTimeStr(article.regDate));
			System.out.println("수정날짜 : " + util.getNowDateTimeStr(article.updateDate));
			System.out.println("제목 : " + article.title);
			System.out.println("내용 : " + article.body);
			
		// 수정
		} else if (cmd.startsWith("article modify ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			System.out.println("--- 게시물 수정 ---");
			
			System.out.print("새 제목 : ");
			String title = sc.nextLine();
			
			System.out.print("새 내용 : ");
			String body = sc.nextLine();
			
			SecSql sql = new SecSql();
			
			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append("WHERE id = ?", id);
			
			DBUtil.update(conn, sql);		// 수정 실행
			
			System.out.println(id + "번 글이 수정되었습니다.");
			
		// 삭제
		} else if (cmd.startsWith("article delete ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			System.out.println("--- 게시물 삭제 ---");
			
			SecSql sql = new SecSql();
			
			// 삭제 시, 해당 번호 글이 없어도 쿼리는 실행되기 때문에(적용되지는 않음)
			// 조회 후 삭제한다.
			sql.append("SELECT COUNT(*) FROM article");
			sql.append("WHERE id = ?", id);
			
			int articlesCount = DBUtil.selectRowIntValue(conn, sql); // 0 (없음) 아니면 1 (있음)
			
			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다.");
				return 0;	// 0인 이유 : doAction이 int 타입이고 -1이면 프로그램 종료.
			}
			
			sql = new SecSql();
			
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);
			
			DBUtil.delete(conn, sql);		// 삭제 실행
			
			System.out.println(id + "번 글이 삭제되었습니다.");
			
		}
	
		return 0;	// doAction
	}
}