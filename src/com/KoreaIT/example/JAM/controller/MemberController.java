package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		super(sc);		// super() : 부모의 생성자 호출
		memberService = new MemberService(conn);
	}
	
	public void doJoin(String cmd) {
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
			
			// 아이디 중복 체크
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용 중인 아이디입니다.");
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
			
			// "이름" 필수 입력
			if (name.length() == 0) {
				System.out.println("이름을 입력해 주세요.");
				continue;
			}
			break;
		}
		
		int id = memberService.doJoin(loginId, loginPw, name);
		
		System.out.println(name + " 회원님, 가입되었습니다.");	
	}
}
