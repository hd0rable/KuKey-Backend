package com.example.Kukey_Backend.domain.openRequest.service;

import com.example.Kukey_Backend.domain.openRequest.domain.dto.response.PatchOpenRequestResponse;
import com.example.Kukey_Backend.domain.openRequest.domain.repository.OpenRequestRepository;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.handler.DiscordMessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.Kukey_Backend.domain.notification.domain.dto.response.EventMessage.OPEN_REQUEST_EVENT;
import static com.example.Kukey_Backend.domain.space.domain.OpenStatus.OPEN;
import static com.example.Kukey_Backend.global.entity.RequestStatus.REQUESTED;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OpenRequestService {

    private final OpenRequestRepository openRequestRepository;
    private final SpaceRepository spaceRepository;
    private final DiscordMessageProvider discordMessageProvider;

    /**
     * 개방 요청하기
     */
    public PatchOpenRequestResponse openRequest(Long spaceId) {

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                () -> new GlobalException(CANNOT_FOUND_SPACE)
        );

        //이미 열려있는 실습실 예외처리
        if (space.getOpenStatus().equals(OPEN))
            throw new GlobalException(ALREADY_OPEN);

        //이미 개방요청이 되어있는 실습실 예외처리
        if (space.getOpenRequestStatus().equals(REQUESTED))
            throw new GlobalException(ALREADY_OPEN_REQUESTED);

        space.setOpenRequestStatus(REQUESTED);
        spaceRepository.save(space);

        //알림 전송
        discordMessageProvider.sendMessage
                (space.getBuildingName().getBuildingName()+" "+space.getSpaceDisplayName()+OPEN_REQUEST_EVENT.getMessage());

        return PatchOpenRequestResponse.builder()
                .spaceId(spaceId)
                .openRequestStatus("REQUESTED")
                .build();
    }
}
