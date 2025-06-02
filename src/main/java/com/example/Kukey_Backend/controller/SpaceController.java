package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.reservation.domain.dto.response.GetSpacesReservationInfoResponse;
import com.example.Kukey_Backend.domain.reservation.service.ReservationService;
import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.service.SpaceService;
import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    private final SpaceService spaceService;
    private final ReservationService reservationService;

    //실습실 개방 조회
    @GetMapping("/open")
    public BaseResponse<GetSpacesOpenInfoResponse> getSpacesOpenInfo(){
        return BaseResponse.ok(spaceService.getSpacesOpenInfo());
    }

    //실습실 예약 조회
    @GetMapping("/reservation")
    public BaseResponse<GetSpacesReservationInfoResponse> getSpacesReservationInfo(@RequestParam("Date")
                                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return BaseResponse.ok(reservationService.getSpacesReservationInfo(date));
    }
}
