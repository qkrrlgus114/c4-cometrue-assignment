package org.c4marathon.assignment.global.exception.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionInfo {

	/**
	 * 멤버 익셉션
	 */
	ID_DUPLICATED(HttpStatus.CONFLICT, "M-001", "이미 존재하는 아이디입니다."),
	NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "M-002", "이미 존재하는 닉네임입니다."),
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, "M-003", "이미 존재하는 이메일입니다."),
	PHONE_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "M-004", "이미 존재하는 휴대폰 번호입니다."),
	ERROR_REGISTER_MEMBER(HttpStatus.BAD_REQUEST, "M-005", "회원가입 중 에러가 발생했습니다."),
	DUPLICATE_MEMBER_DATA(HttpStatus.CONFLICT, "M-006", "중복된 데이터가 존재합니다.");

	private HttpStatus httpStatus;
	private String code;
	private String message;

	ExceptionInfo(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}
}
