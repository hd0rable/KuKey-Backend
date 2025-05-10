package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.reservation.domain.dto.request.PostReservationToSpaceRequest;
import com.example.Kukey_Backend.domain.reservation.domain.dto.response.GetReservationInfoResponse;
import com.example.Kukey_Backend.domain.reservation.service.ReservationService;
import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    //학번으로 예약 조회
    @Validated
    @GetMapping("")
    public BaseResponse<GetReservationInfoResponse> getReservationInfo(@RequestParam @NotBlank(message = "학번은 필수입니다.")
                                                                           @Size(max=10,message = "학번은 10자 이하로 입력해주세요")
                                                                           final String studentNumber,
                                                                       @RequestParam @NotBlank(message = "이름은 필수입니다.")
                                                                       @Size(max=30,message = "예약자 이름은 30자 이하로 입력해주세요")
                                                                       final String studentName) {
        return BaseResponse.ok(reservationService.getReservationInfo(studentNumber,studentName));
    }

    //예약 취소하기
    @DeleteMapping("/{reservationId}")
    public BaseResponse<Void> deleteReservation(@PathVariable final Long reservationId) {
        return BaseResponse.ok(reservationService.deleteReservation(reservationId));
    }

}
