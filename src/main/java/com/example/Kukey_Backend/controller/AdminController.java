package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.service.SpaceService;
import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@RoleRequired("ADMIN")
public class AdminController {

    private final SpaceService spaceService;

    @GetMapping("/spaces")
    public BaseResponse<GetSpacesOpenInfoResponse> getSpacesOpenInfo(){
        return BaseResponse.ok(spaceService.getSpacesOpenInfo());
    }
}
