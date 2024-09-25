package org.c4marathon.assignment.global.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";

	private String status;
	private T data;
	private String message;

	public ApiResponse(String status, T data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public ApiResponse(String status, T data) {
		this.status = status;
		this.data = data;
	}

	public static <T> ApiResponse<T> createSuccess(T data) {
		return new ApiResponse<>(SUCCESS_STATUS, data);
	}

	public static ApiResponse<Void> createSuccessNoData() {
		return new ApiResponse<>(SUCCESS_STATUS, null);
	}

	// 예외 발생으로 API 호출 실패시 반환
	public static ApiResponse<Void> createError(String message) {
		return new ApiResponse<>(ERROR_STATUS, null, message);
	}
}
