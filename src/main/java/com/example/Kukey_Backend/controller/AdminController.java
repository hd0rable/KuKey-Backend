package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.admin.domain.dto.response.PatchOpenChangeResponse;
import com.example.Kukey_Backend.domain.admin.service.AdminService;
import com.example.Kukey_Backend.domain.keyLocation.domain.dto.request.PostKeyLocationUploadRequest;
import com.example.Kukey_Backend.domain.keyLocation.domain.dto.response.PostKeyLocationUploadResponse;
import com.example.Kukey_Backend.domain.keyLocation.service.KeyLocationService;
import com.example.Kukey_Backend.domain.notification.service.S3Service;
import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.service.SpaceService;
import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@RoleRequired("ADMIN")
public class AdminController {

    private final SpaceService spaceService;
    private final KeyLocationService keyLocationService;
    private final AdminService adminService;
    private final S3Service s3Service;

    //관리자 개방 관리 조회
    @GetMapping("/spaces")
    public BaseResponse<GetSpacesOpenInfoResponse> getSpacesOpenInfo(){
        return BaseResponse.ok(spaceService.getSpacesOpenInfo());
    }

    //관리자 실습실 관리 (잠금 개방 상태 변경)
    @PatchMapping("/open-change/{spaceId}")
    public BaseResponse<PatchOpenChangeResponse> changeSpaceOpenStatus(@PathVariable("spaceId") Long spaceId){
        return  BaseResponse.ok(adminService.changeSpaceOpenStatus(spaceId));
    }

    //카드키 이미지 업로드
    @PostMapping("/key/images")
    public BaseResponse<?> s3ImageUpload(@RequestPart(value = "image", required = false) MultipartFile image){
        return BaseResponse.ok(s3Service.getImageFromUser(image));
    }

    //카드키 위치 기록 등록
    @PostMapping("/key/upload")
    public BaseResponse<PostKeyLocationUploadResponse> keyLocationUpload(@Valid @RequestBody final PostKeyLocationUploadRequest postKeyLocationUploadRequest){
        return BaseResponse.ok(keyLocationService.keyLocationUpload(postKeyLocationUploadRequest));
    }


}
