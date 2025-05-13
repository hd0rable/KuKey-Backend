package com.example.Kukey_Backend.domain.reservation.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

@Builder
public record TimeSlot(
        @JsonFormat(pattern = "HH:mm") String startTime,
        @JsonFormat(pattern = "HH:mm") String endTime
) {
}
