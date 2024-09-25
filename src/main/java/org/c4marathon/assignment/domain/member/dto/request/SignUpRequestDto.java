package org.c4marathon.assignment.domain.member.dto.request;

import jakarta.validation.constraints.*;

public record SignUpRequestDto(
		@NotBlank(message = "아이디를 입력해주세요.")
		@Size(max = 30, message = "아이디는 최대 30자까지 가능합니다.")
		String id,

		@NotBlank(message = "비밀번호를 입력해주세요.")
		@Size(max = 20, message = "비밀번호는 최대 20자까지 가능합니다.")
		String password,

		@NotBlank(message = "이메일을 입력해주세요.")
		@Email(message = "이메일 형식이 아닙니다.")
		@Size(max = 30, message = "이메일은 최대 30자까지 가능합니다.")
		String email,

		@NotBlank(message = "휴대폰 번호를 입력해주세요.")
		@Size(max = 13, message = "휴대폰 번호는 최대 13자까지 가능합니다.")
		String phoneNumber,

		@NotBlank(message = "닉네임을 입력해주세요.")
		@Size(max = 20, message = "닉네임은 최대 20자까지 가능합니다.")
		String nickname,

		@NotNull(message = "나이를 입력해주세요.")
		@Min(value = 1, message = "나이는 최소 1살이어야 합니다.")
		@Max(value = 150, message = "나이는 150살을 넘을 수 없습니다.")
		Integer age,

		String profileImage) {

	public SignUpRequestDto {
		if (profileImage == null || profileImage.isBlank()) {
			profileImage = "http:localhost:8080/imageUrl";
		}
	}
}
