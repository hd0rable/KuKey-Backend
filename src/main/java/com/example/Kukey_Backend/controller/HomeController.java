package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public BaseResponse<Void> home() {
        return BaseResponse.ok();
    }
}
