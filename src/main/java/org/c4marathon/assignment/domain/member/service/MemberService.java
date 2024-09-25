package org.c4marathon.assignment.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;

public interface MemberService {

	// 회원가입
	void signUp(HttpServletRequest request, SignUpRequestDto signUpRequestDto);

	// 아이디 중복체크
	void isIdDuplicate(String id);

	// 이메일 중복체크
	void isEmailDuplicate(String email);

	// 닉네임 중복체크
	void isNicknameDuplicate(String nickname);

	// 휴대폰 번호 중복체크
	void isPhoneNumberDuplicate(String phoneNumber);
}
