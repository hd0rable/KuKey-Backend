package com.example.Kukey_Backend.domain.space.service;

import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.domain.dto.response.spaceOpenInfo;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    /**
     * 실습실 개방 정보 조회
     */
    public GetSpacesOpenInfoResponse getSpacesOpenInfo() {
        List<spaceOpenInfo> spaceList = spaceRepository.findAll().stream()
                .map(space -> spaceOpenInfo.builder()
                        .spaceId(space.getSpaceId())
                        .buildingName(space.getBuildingName().getBuildingName())
                        .spaceDisplayName(space.getSpaceDisplayName())
                        .openStatus(space.getOpenStatus().toString())
                        .openRequestStatus(space.getOpenRequestStatus().toString())
                        .ReservationStatus(space.getReservationStatus().toString())
                        .build())
                .collect(Collectors.toList());

        return GetSpacesOpenInfoResponse.of(spaceList);
    }
}
