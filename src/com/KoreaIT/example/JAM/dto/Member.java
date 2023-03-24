package com.KoreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member extends Object {
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String loginId;
	public String loginPw;
	public String name;
	
	public Member(int id, LocalDateTime regDate, LocalDateTime updateDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}
	
	public Member(Map<String, Object> articleMap) {
		// Map<String, Object> (Map<필드, 값>)
		// String : 필드명 (속성명)
		// Object : 모든 타입(어떤 타입이 들어올지 모르기 때문에 Object로 설정)
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.loginId = (String) articleMap.get("loginId");
		this.loginPw = (String) articleMap.get("loginPw");
		this.name = (String) articleMap.get("name");
		// .get(필드명) -> 가져올 때 형 변환 필요
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", loginId=" + loginId
				+ ", loginPw=" + loginPw + ", name=" + name + "]";
	}
}
