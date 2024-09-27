package org.c4marathon.assignment.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.domain.member.dto.request.LoginRequestDto;
import org.c4marathon.assignment.domain.member.dto.request.SignUpRequestDto;
import org.c4marathon.assignment.domain.member.dto.response.LoginResponseDto;
import org.c4marathon.assignment.domain.member.service.MemberService;
import org.c4marathon.assignment.global.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class MemberController {

	private final MemberService memberService;

	// 회원가입
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse<SignUpRequestDto>> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
		memberService.signUp(signUpRequestDto);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(signUpRequestDto));
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponseDto>> login(HttpServletRequest request, @RequestBody @Valid LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = memberService.login(request, loginRequestDto);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(loginResponseDto));
	}

	// 아이디 중복 확인
	@GetMapping("/exists/id")
	public ResponseEntity<ApiResponse<Void>> idCheck(@RequestParam String id) {
		memberService.isIdDuplicate(id);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccessNoData());
	}

	// 이메일 중복 확인
	@GetMapping("/exists/email")
	public ResponseEntity<ApiResponse<Void>> emailCheck(@RequestParam @Email String email) {
		memberService.isEmailDuplicate(email);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccessNoData());
	}

	// 닉네임 중복 확인
	@GetMapping("/exists/nickname")
	public ResponseEntity<ApiResponse<Void>> nicknameCheck(@RequestParam String nickname) {
		memberService.isNicknameDuplicate(nickname);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccessNoData());
	}

	// 휴대폰 번호 중복 확인
	@GetMapping("/exists/phonenumber")
	public ResponseEntity<ApiResponse<Void>> phoneNumberCheck(@RequestParam String phoneNumber) {
		memberService.isPhoneNumberDuplicate(phoneNumber);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccessNoData());
	}

}
