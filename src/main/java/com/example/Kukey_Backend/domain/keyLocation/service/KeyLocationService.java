package com.example.Kukey_Backend.domain.keyLocation.service;

import com.example.Kukey_Backend.domain.keyLocation.domain.KeyLocation;
import com.example.Kukey_Backend.domain.keyLocation.domain.dto.request.PostKeyLocationUploadRequest;
import com.example.Kukey_Backend.domain.keyLocation.domain.dto.response.PostKeyLocationUploadResponse;
import com.example.Kukey_Backend.domain.keyLocation.domain.repository.KeyLocationRepository;
import com.example.Kukey_Backend.domain.space.domain.BuildingName;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import com.example.Kukey_Backend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_SPACE;

@Service
@Transactional
@RequiredArgsConstructor
public class KeyLocationService {

    private final SpaceRepository spaceRepository;
    private final KeyLocationRepository keyLocationRepository;

    /**
     * 카드키 위치 기록 등록
     */
    public PostKeyLocationUploadResponse keyLocationUpload(PostKeyLocationUploadRequest postKeyLocationUploadRequest) {

        //건물명(String) → enum 변환
        BuildingName buildingName = BuildingName.from(postKeyLocationUploadRequest.buildingName());

        //해당 건물의 모든 Space 조회
        List<Space> spaceList = spaceRepository.findAllByBuildingName(buildingName)
                .orElseThrow(() -> new GlobalException(CANNOT_FOUND_SPACE));

        //각 Space에 연결된 이전 KeyLocation 삭제 및 새로운 KeyLocation 으로 업데이트
        spaceList.forEach(space -> {
            keyLocationRepository.findBySpace(space).ifPresent(keyLocationRepository::delete);

            KeyLocation newKeyLocation =
                    KeyLocation.builder()
                            .space(space)
                            .adminName(postKeyLocationUploadRequest.adminName())
                            .imageUrl(postKeyLocationUploadRequest.imageUrl())
                            .description(postKeyLocationUploadRequest.description())
                            .build();

            keyLocationRepository.save(newKeyLocation);
        });

        return PostKeyLocationUploadResponse.builder()
                .buildingName(postKeyLocationUploadRequest.buildingName())
                .keyLocationId(keyLocationRepository.findBySpace(spaceList.get(0)).get().getKeyLocationId())
                .build();

    }
}
