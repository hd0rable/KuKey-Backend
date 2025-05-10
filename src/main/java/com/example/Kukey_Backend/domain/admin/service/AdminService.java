package com.example.Kukey_Backend.domain.admin.service;

import com.example.Kukey_Backend.domain.admin.domain.dto.response.PatchOpenChangeResponse;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import com.example.Kukey_Backend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.Kukey_Backend.domain.space.domain.OpenStatus.LOCKED;
import static com.example.Kukey_Backend.domain.space.domain.OpenStatus.OPEN;
import static com.example.Kukey_Backend.global.entity.RequestStatus.NONE;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_SPACE;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final SpaceRepository spaceRepository;

    public PatchOpenChangeResponse changeSpaceOpenStatus(Long spaceId) {

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                () -> new GlobalException(CANNOT_FOUND_SPACE)
        );

        if(space.getOpenStatus().equals(LOCKED)){
            space.setOpenRequestStatus(NONE);
            space.setOpenStatus(OPEN);
        }
        else{
            space.setOpenStatus(LOCKED);
        }
        spaceRepository.save(space);

        return PatchOpenChangeResponse.builder()
                .spaceId(spaceId)
                .openStatus(space.getOpenStatus().toString())
                .build();

    }
}
