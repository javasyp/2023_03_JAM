package com.KoreaIT.example.JAM.controller;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dto.Member;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;
	
	public MemberController() {
		memberService = Container.memberService;
	}
	
	public void login(String cmd) {
		System.out.println("--- 로그인 ---");
		
		String loginId = null;
		
		while (true) {
			
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해 주세요.");
				continue;
			}
			
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isLoginIdDup == false) {
				System.out.println(loginId + "는(은) 존재하지 않습니다.");
				continue;
			}
			
			break;
		}
		
		Member member = memberService.getMemberByloginId(loginId);	// Map<> 써도 됨
		
		int maxTryCount = 3;
		int tryCount = 0;
		
		String loginPw = null;
		
		while (true) {
			// 비밀번호 입력 횟수 제한
			if (tryCount > maxTryCount) {
				System.out.println("비밀번호 확인 후 다시 입력해 주세요.");
				break;
			}
			
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				tryCount++;
				System.out.println("비밀번호를 입력해 주세요.");
				continue;
			}
			
			if (member.loginPw.equals(loginPw) == false) {
				tryCount++;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			
			System.out.println(member.name + " 님, 환영합니다.");
			break;
		}
		
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
		
		System.out.println(id + "번 회원님, 가입되었습니다.");	
	}

}
