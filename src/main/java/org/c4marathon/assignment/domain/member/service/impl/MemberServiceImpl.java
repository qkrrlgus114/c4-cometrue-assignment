package org.c4marathon.assignment.domain.member.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;
import org.c4marathon.assignment.domain.member.mapper.MemberMapper;
import org.c4marathon.assignment.domain.member.service.MemberService;
import org.c4marathon.assignment.global.exception.ExceptionModule;
import org.c4marathon.assignment.global.exception.info.ExceptionInfo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	// 회원가입
	@Override
	@Transactional
	public void signUp(HttpServletRequest request, final SignUpRequestDto signUpRequestDto) {
		try {
			int result = memberMapper.insertMember(signUpRequestDto);
			if (result <= 0) {
				throw new ExceptionModule(ExceptionInfo.ERROR_REGISTER_MEMBER, "에러 발생");
			}

		} catch (DataIntegrityViolationException e) {
			throw new ExceptionModule(ExceptionInfo.DUPLICATE_MEMBER_DATA, "회원가입 데이터 중복 오류 발생");
		}
	}

	// 아이디 중복체크
	@Override
	@Transactional(readOnly = true)
	public void isIdDuplicate(final String id) {
		if (memberMapper.checkIdDuplicate(id) > 0) {
			throw new ExceptionModule(ExceptionInfo.ID_DUPLICATED, "아이디 : " + id + "중복 회원가입 시도");
		}
	}

	// 이메일 중복체크
	@Override
	@Transactional(readOnly = true)
	public void isEmailDuplicate(final String email) {
		if (memberMapper.checkEmailDuplicate(email) > 0) {
			throw new ExceptionModule(ExceptionInfo.EMAIL_DUPLICATED, "이메일 : " + email + "중복 회원가입 시도");
		}
	}

	// 닉네임 중복체크
	@Override
	@Transactional(readOnly = true)
	public void isNicknameDuplicate(final String nickname) {
		if (memberMapper.checkNicknameDuplicate(nickname) > 0) {
			throw new ExceptionModule(ExceptionInfo.NICKNAME_DUPLICATED, "닉네임 : " + nickname + "중복 회원가입 시도");
		}
	}

	// 휴대폰 번호 중복체크
	@Override
	@Transactional(readOnly = true)
	public void isPhoneNumberDuplicate(final String phoneNumber) {
		if (memberMapper.checkPhoneNumberDuplicate(phoneNumber) > 0) {
			throw new ExceptionModule(ExceptionInfo.PHONE_NUMBER_DUPLICATED, "휴대폰 번호 : " + phoneNumber + "중복 회원가입 시도");
		}
	}
}
