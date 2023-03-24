package com.KoreaIT.example.JAM.dao;

import java.util.Map;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dto.Member;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberDao {
	public MemberDao() {
	}
	
	// 로그인 시 회원인지 확인
	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE loginId = ?", loginId);
		
		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);
		
		if (memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);
	}
	
	// 회원가입 시 아이디 중복 체크
	public boolean isLoginIdDup(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowBooleanValue(Container.conn, sql);
	}
	
	// 회원가입
	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);

		return DBUtil.insert(Container.conn, sql);
	}

}