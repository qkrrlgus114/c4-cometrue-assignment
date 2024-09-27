package org.c4marathon.assignment.domain.member.dto.response;

public record LoginResponseDto(
		String memberPk,
		String id,
		String password,
		String email,
		String phoneNumber,
		String nickname,
		Integer age,
		String profileImage
) {
}
