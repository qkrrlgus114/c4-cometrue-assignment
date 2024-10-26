package org.c4marathon.assignment.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.c4marathon.assignment.domain.member.dto.LoginHistoryDto;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;
import org.c4marathon.assignment.domain.member.dto.response.LoginResponseDto;

@Mapper
public interface MemberMapper {

	/**
	 * 회원가입 데이터 중복 검사
	 **/
	int checkIdDuplicate(@Param("id") String id);

	int checkEmailDuplicate(String email);

	int checkNicknameDuplicate(String nickname);

	int checkPhoneNumberDuplicate(String phoneNumber);

	/**
	 * 멤버
	 **/
	LoginResponseDto selectMember(String id);

	int insertMember(SignUpRequestDto signUpRequestDto);

	/**
	 * 로그인 히스토리
	 **/
	int insertLoginHistory(LoginHistoryDto loginHistoryDto);

}
