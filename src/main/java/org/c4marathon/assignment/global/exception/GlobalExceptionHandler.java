package org.c4marathon.assignment.global.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(ExceptionModule.class)
	public ResponseEntity<ApiResponse<Void>> handleException(ExceptionModule e) {
		return ResponseEntity.status(e.getExceptionInfo().getHttpStatus()).body(ApiResponse.createError(e.getExceptionInfo().getMessage()));
	}

	// Validated 유효성 검사 실패
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
		String errorMessage = e.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage)
				.findFirst()
				.orElse("");
		log.warn("유효성 검사 실패 예외 발생 : ", errorMessage);

		return ResponseEntity.status(400).body(ApiResponse.createError(errorMessage));
	}

	// Valid 유효성 검사 실패
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult().getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.findFirst() // 스트림에서 첫 번째 요소만 가져옴
				.orElse("유효성 검사 오류 발생");

		log.warn("유효성 검사 실패 예외 발생 : ", errorMessage);

		return ResponseEntity.status(400).body(ApiResponse.createError(errorMessage));
	}
}
