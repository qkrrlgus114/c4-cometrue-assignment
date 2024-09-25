package org.c4marathon.assignment.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.global.exception.info.ExceptionInfo;

@Getter
@NoArgsConstructor
public class ExceptionModule extends RuntimeException {

	private ExceptionInfo exceptionInfo;

	// 개발자에게 보여지는 로그
	private String log;

	public ExceptionModule(ExceptionInfo exceptionInfo, String log) {
		this.exceptionInfo = exceptionInfo;
		this.log = log;
	}
}
