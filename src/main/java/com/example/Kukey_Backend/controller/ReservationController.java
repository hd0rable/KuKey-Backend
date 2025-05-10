package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.reservation.domain.dto.request.PostReservationToSpaceRequest;
import com.example.Kukey_Backend.domain.reservation.service.ReservationService;
import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@RoleRequired("USER")
public class ReservationController {

    private final ReservationService reservationService;

    //실습실 예약하기
    @PostMapping("/{spaceId}")
    public BaseResponse<Void> reservationToSpace(@PathVariable final Long spaceId,
                                   @Valid @RequestBody final PostReservationToSpaceRequest postReservationToSpaceRequest) {
        return BaseResponse.ok(reservationService.reservationToSpace(spaceId,postReservationToSpaceRequest));
    }
}
