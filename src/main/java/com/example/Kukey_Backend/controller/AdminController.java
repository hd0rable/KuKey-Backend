package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.admin.domain.dto.response.PatchOpenChangeResponse;
import com.example.Kukey_Backend.domain.admin.service.AdminService;
import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.service.SpaceService;
import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@RoleRequired("ADMIN")
public class AdminController {

    private final SpaceService spaceService;
    private final AdminService adminService;

    @GetMapping("/spaces")
    public BaseResponse<GetSpacesOpenInfoResponse> getSpacesOpenInfo(){
        return BaseResponse.ok(spaceService.getSpacesOpenInfo());
    }

    @PatchMapping("/open-change/{spaceId}")
    public BaseResponse<PatchOpenChangeResponse> changeSpaceOpenStatus(@PathVariable("spaceId") Long spaceId){
        return  BaseResponse.ok(adminService.changeSpaceOpenStatus(spaceId));
    }
}
