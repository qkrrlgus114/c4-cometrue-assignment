package org.c4marathon.assignment.domain.member.dto.response;

import lombok.Builder;

@Builder
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
