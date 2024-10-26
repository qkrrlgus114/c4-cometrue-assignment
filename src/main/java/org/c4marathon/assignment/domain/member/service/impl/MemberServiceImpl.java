package org.c4marathon.assignment.domain.member.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.domain.member.dto.LoginHistoryDto;
import org.c4marathon.assignment.domain.member.dto.request.LoginRequestDto;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;
import org.c4marathon.assignment.domain.member.dto.response.LoginResponseDto;
import org.c4marathon.assignment.domain.member.mapper.MemberMapper;
import org.c4marathon.assignment.domain.member.service.MemberService;
import org.c4marathon.assignment.global.exception.ExceptionModule;
import org.c4marathon.assignment.global.exception.info.ExceptionInfo;
import org.c4marathon.assignment.global.util.WordFilterUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@Override
	@Transactional
	public void signUp(final SignUpRequestDto signUpRequestDto) {
		try {
			if(WordFilterUtil.containsBannedWord(signUpRequestDto.getId()) || WordFilterUtil.containsBannedWord(signUpRequestDto.getNickname())){
				throw new ExceptionModule(ExceptionInfo.BANNED_WORD_INPUT, "사용 불가능한 단어 포함");
			}

			SignUpRequestDto signUpDTO = SignUpRequestDto.builder()
					.id(signUpRequestDto.getId())
					.password(passwordEncoder.encode(signUpRequestDto.getPassword()))
					.age(signUpRequestDto.getAge())
					.email(signUpRequestDto.getEmail())
					.phoneNumber(signUpRequestDto.getPhoneNumber())
					.nickname(signUpRequestDto.getNickname())
					.profileImage(signUpRequestDto.getProfileImage()).build();

			int result = memberMapper.insertMember(signUpDTO);

			if (result <= 0) {
				throw new ExceptionModule(ExceptionInfo.ERROR_REGISTER_MEMBER, "에러 발생");
			}

		} catch (DataIntegrityViolationException e) {
			throw new ExceptionModule(ExceptionInfo.DUPLICATE_MEMBER_DATA, "회원가입 데이터 중복 오류 발생");
		}
	}

	// 로그인
	@Override
	@Transactional
	public LoginResponseDto login(HttpServletRequest request, LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = memberMapper.selectMember(loginRequestDto.id());

		if (loginResponseDto == null) {
			throw new ExceptionModule(ExceptionInfo.NOT_MATCH_ID, "아이디 : " + loginRequestDto.id() + ", 로그인(아이디 불일치)");
		}

		if (!passwordEncoder.matches(loginRequestDto.password(), loginResponseDto.password())) {
			throw new ExceptionModule(ExceptionInfo.NOT_MATCH_PASSWORD, "아이디 : " + loginRequestDto.id() + ", 로그인(비밀번호 불일치)");
		}

		LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
				.memberPk(loginResponseDto.memberPk())
				.loginDate(LocalDateTime.now())
				.ip(request.getRemoteAddr()).build();

		if (memberMapper.insertLoginHistory(loginHistoryDto) <= 0) {
			throw new ExceptionModule(ExceptionInfo.UNKNOWN_ERROR, "아이디 : " + loginRequestDto.id() + ", 로그인 기록 생성 실패");
		}

		HttpSession session = request.getSession();
		session.setAttribute("memberPk", loginResponseDto.memberPk());
		session.setMaxInactiveInterval(60 * 60);

		return loginResponseDto;
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
