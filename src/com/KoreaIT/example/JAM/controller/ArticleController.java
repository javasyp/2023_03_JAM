package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.service.ArticleService;
import com.KoreaIT.example.JAM.util.util;

public class ArticleController extends Controller {
	private ArticleService articleService;
	
	public ArticleController(Connection conn, Scanner sc) {
		super(sc);		// super() : 부모의 생성자 호출
		articleService = new ArticleService(conn);
	}

	public void doWrite(String cmd) {
		System.out.println("--- 게시물 작성 ---");
		
		System.out.print("제목 : ");
		String title = sc.nextLine();
		
		System.out.print("내용 : ");
		String body = sc.nextLine();
		
		int id = articleService.doWrite(title, body);
		
		System.out.println(id + "번 글이 생성되었습니다.");
		
	}

	public void showList(String cmd) {
		System.out.println("--- 게시물 목록 ---");
		
		List<Article> articles = articleService.getArticlesCount();
		
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
		
		Map<String, Object> articleMap = articleService.getArticleById(id);
		
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
		
		int articlesCount = articleService.getArticlesCount(id);

		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		
		System.out.println("--- 게시물 수정 ---");
		
		System.out.print("새 제목 : ");
		String title = sc.nextLine();
		
		System.out.print("새 내용 : ");
		String body = sc.nextLine();
		
		articleService.doModify(id, title, body);
		
		System.out.println(id + "번 글이 수정되었습니다.");
		
	}

	public void doDelete(String cmd) {
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		System.out.println("--- 게시물 삭제 ---");
		
		int articlesCount = articleService.getArticlesCount(id);
		
		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;	// 0인 이유 : doAction이 int 타입이고 -1이면 프로그램 종료.
		}
		
		articleService.doDelete(id);
		
		System.out.println(id + "번 글이 삭제되었습니다.");
		
	}

}
