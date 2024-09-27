package org.c4marathon.assignment.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginHistoryDto(
		String memberPk,
		LocalDateTime loginDate,
		String ip
) {
}
