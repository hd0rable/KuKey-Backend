package com.example.Kukey_Backend.domain.space.domain.dto.response;

import java.util.List;

public record GetSpacesOpenInfoResponse(
        List<spaceOpenInfo> spaceList
) {
    public static GetSpacesOpenInfoResponse of(List<spaceOpenInfo> spaceList) {
        return new GetSpacesOpenInfoResponse(spaceList);
    }
}
