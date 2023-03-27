package com.KoreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object {
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public int memberId;
	public String title;
	public String body;
	public int hit;
	
	public String extra_writer;
	
	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
	public Article(Map<String, Object> articleMap) {
		// Map<String, Object> (Map<필드, 값>)
		// String : 필드명 (속성명)
		// Object : 모든 타입(어떤 타입이 들어올지 모르기 때문에 Object로 설정)
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.hit = (int) articleMap.get("hit");
		// .get(필드명) -> 가져올 때 형 변환 필요
		
		if (articleMap.get("extra_writer") != null) {
			this.extra_writer = (String) articleMap.get("extra_writer");
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", memberId=" + memberId
				+ ", title=" + title + ", body=" + body + "]";
	}
}
