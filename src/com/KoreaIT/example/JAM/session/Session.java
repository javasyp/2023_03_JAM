package com.KoreaIT.example.JAM.session;

import com.KoreaIT.example.JAM.dto.Member;

public class Session {
	
	public Member loginedMember;
	
	public int loginedMemberId;
	
	public Session() {
		loginedMemberId = -1;
	}
	
	public void login(Member member) {
		loginedMember = member;
		loginedMemberId = member.id;
	}
	
	public void logout() {
		loginedMember = null;
		loginedMemberId = -1;
		System.out.println("로그아웃 되었습니다.");
	}

	public boolean isLogined() {
		return loginedMemberId != -1;	// loginedMemberId이 -1이 아니다!가 참이면 현재 로그인 상태
	}

}