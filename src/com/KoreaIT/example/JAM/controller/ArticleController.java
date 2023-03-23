package com.KoreaIT.example.JAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;
import com.KoreaIT.example.JAM.util.util;

public class ArticleController extends Controller {

	public void doWrite(String cmd) {
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
		
	}

	public void showList(String cmd) {
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
			return;
		}

		System.out.println("번호  /  제목");
		
		for (Article article : articles) {
			System.out.printf("%d   /   %s\n", article.id, article.title);
		}
		
	}

	public void showDetail(String cmd) {

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
			return;
		}
		
		Article article = new Article(articleMap);
		
		System.out.println("번호 : " + article.id);
		System.out.println("작성날짜 : " + util.getNowDateTimeStr(article.regDate));
		System.out.println("수정날짜 : " + util.getNowDateTimeStr(article.updateDate));
		System.out.println("제목 : " + article.title);
		System.out.println("내용 : " + article.body);
		
	}

	public void doModify(String cmd) {
		
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
		
	}

	public void doDelete(String cmd) {
		
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
			return;	// 0인 이유 : doAction이 int 타입이고 -1이면 프로그램 종료.
		}
		
		sql = new SecSql();
		
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(conn, sql);		// 삭제 실행
		
		System.out.println(id + "번 글이 삭제되었습니다.");
		
	}

}
