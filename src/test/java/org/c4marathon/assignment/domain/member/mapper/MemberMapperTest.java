package org.c4marathon.assignment.domain.member.mapper;

import org.c4marathon.assignment.domain.member.dto.LoginHistoryDto;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;
import org.c4marathon.assignment.domain.member.dto.response.LoginResponseDto;
import org.c4marathon.assignment.global.exception.ExceptionModule;
import org.c4marathon.assignment.global.exception.info.ExceptionInfo;
import org.c4marathon.assignment.global.util.WordFilterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
class MemberMapperTest {

	@Autowired
	private MemberMapper memberMapper;

	private LoginResponseDto loginResponseDto;

	@BeforeEach
	void before(){
		loginResponseDto = LoginResponseDto.builder()
				.memberPk("1")
				.id("qkrrlgus114")
				.password("1234")
				.email("test@naver.com")
				.phoneNumber("010-8615-6570")
				.nickname("박기현")
				.age(28)
				.profileImage("http://default.url").build();
	}

	@Test
	@DisplayName("아이디 중복체크 쿼리(중복된 아이디 미존재)")
	void checkIdDuplicateSuccess() {
		String id = "test123";

		int result = memberMapper.checkIdDuplicate(id);

		Assertions.assertEquals(0, result);
	}

	@Test
	@DisplayName("아이디 중복체크 쿼리(중복된 아이디 존재)")
	void checkIdDuplicateFail() {
		String id = "qkrrlgus114";

		int result = memberMapper.checkIdDuplicate(id);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("이메일 중복체크 쿼리(중복된 이메일 미존재)")
	void checkEmailDuplicateSuccess() {
		String email = "test123@naver.com";

		int result = memberMapper.checkEmailDuplicate(email);

		Assertions.assertEquals(0, result);
	}

	@Test
	@DisplayName("이메일 중복체크 쿼리(중복된 이메일 존재)")
	void checkEmailDuplicateFail() {
		String email = "test@naver.com";

		int result = memberMapper.checkEmailDuplicate(email);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("닉네임 중복체크 쿼리(중복된 닉네임 미존재)")
	void checkNicknameDuplicateSuccess() {
		String nickname = "닉네임이다";

		int result = memberMapper.checkNicknameDuplicate(nickname);

		Assertions.assertEquals(0, result);
	}

	@Test
	@DisplayName("닉네임 중복체크 쿼리(중복된 닉네임 존재)")
	void checkNicknameDuplicateFail() {
		String nickname = "박기현";

		int result = memberMapper.checkNicknameDuplicate(nickname);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("휴대폰 번호 중복체크 쿼리(중복된 휴대폰 번호 미존재)")
	void checkPhoneNumberDuplicateSuccess() {
		String phoneNumber = "010-1234-6570";

		int result = memberMapper.checkPhoneNumberDuplicate(phoneNumber);

		Assertions.assertEquals(0, result);
	}

	@Test
	@DisplayName("휴대폰 번호 중복체크 쿼리(중복된 휴대폰 번호 존재)")
	void checkPhoneNumberDuplicateFail() {
		String phoneNumber = "010-8615-6570";

		int result = memberMapper.checkPhoneNumberDuplicate(phoneNumber);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("유저 조회 쿼리 성공")
	void selectMemberSuccess() {
		String id = "qkrrlgus114";

		LoginResponseDto selectMember = memberMapper.selectMember(id);

		Assertions.assertNotNull(loginResponseDto);
		Assertions.assertEquals(loginResponseDto, selectMember);
	}

	@Test
	@DisplayName("유저 회원가입 쿼리 성공")
	@Transactional
	void insertMemberSuccess() {
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
				.id("test")
				.password("testtest")
				.age(15)
				.nickname("테스트")
				.profileImage("http://default.url")
				.phoneNumber("010-1234-1234")
				.email("tes123@naver.com")
				.build();

		int result = memberMapper.insertMember(signUpRequestDto);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("로그인 기록 저장 쿼리 성공")
	@Transactional
	void insertLoginHistorySuccess() {
		LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
				.ip("0.0.0.0")
				.loginDate(LocalDateTime.now())
				.memberPk("1").build();

		int result = memberMapper.insertLoginHistory(loginHistoryDto);

		Assertions.assertEquals(1, result);
	}

	@Test
	@DisplayName("필터링 성공")
	@Transactional
	void filterWord(){
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
				.id("test")
				.password("testtest")
				.age(15)
				.nickname("씨발")
				.profileImage("http://default.url")
				.phoneNumber("010-1234-1234")
				.email("tes123@naver.com")
				.build();


		ExceptionModule exception = Assertions.assertThrows(ExceptionModule.class, () -> {
			if (WordFilterUtil.containsBannedWord(signUpRequestDto.getId()) ||
					WordFilterUtil.containsBannedWord(signUpRequestDto.getNickname())) {
				throw new ExceptionModule(ExceptionInfo.BANNED_WORD_INPUT, "사용 불가능한 단어 포함");
			}

			// 금지어가 포함되어 있어야 하므로 회원 등록이 되지 않음
			memberMapper.insertMember(signUpRequestDto);
		});
		Assertions.assertNotNull(exception);
		Assertions.assertEquals("사용 불가능한 단어가 포함되어 있습니다.", exception.getExceptionInfo().getMessage());
	}
}