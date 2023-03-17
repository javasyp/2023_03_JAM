package com.KoreaIT.example.JAM;

public class Article extends Object {
	public int id;
	public String title;
	public String body;
	
	Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", body=" + body + "]";
//		return "Article [id=" + this.id + ", title=" + this.title + ", body=" + this.body + "]";
		// this가 생략되어 있음.
	}
}
