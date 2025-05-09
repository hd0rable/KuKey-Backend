package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.openRequest.domain.dto.response.PatchOpenRequestResponse;
import com.example.Kukey_Backend.domain.openRequest.service.OpenRequestService;
import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-requests")
public class OpenRequestController {

    private final OpenRequestService openRequestService;

    //개방요청하기
    @PatchMapping("/{spaceId}")
    public BaseResponse<PatchOpenRequestResponse> openRequest(@PathVariable("spaceId") final Long spaceId) {
        return BaseResponse.ok(openRequestService.openRequest(spaceId));
    }
}
