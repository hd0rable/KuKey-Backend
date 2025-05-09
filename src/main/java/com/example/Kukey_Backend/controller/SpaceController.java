package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.service.SpaceService;
import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/open")
    public BaseResponse<GetSpacesOpenInfoResponse> getSpacesOpenInfo(){
        return BaseResponse.ok(spaceService.getSpacesOpenInfo());
    }
}
