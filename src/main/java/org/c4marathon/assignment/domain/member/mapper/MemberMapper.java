package org.c4marathon.assignment.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;

@Mapper
public interface MemberMapper {

	int insertMember(SignUpRequestDto signUpRequestDto);

	int checkIdDuplicate(@Param("id") String id);

	int checkEmailDuplicate(String email);

	int checkNicknameDuplicate(String nickname);

	int checkPhoneNumberDuplicate(String phoneNumber);

}
