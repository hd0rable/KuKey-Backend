package com.example.Kukey_Backend.domain.space.service;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import com.example.Kukey_Backend.domain.reservation.domain.repository.ReservationRepository;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.dto.response.GetSpacesOpenInfoResponse;
import com.example.Kukey_Backend.domain.space.domain.dto.response.spaceOpenInfo;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.Kukey_Backend.domain.space.domain.OpenStatus.LOCKED;
import static com.example.Kukey_Backend.domain.space.domain.OpenStatus.OPEN;
import static com.example.Kukey_Backend.global.entity.RequestStatus.ING;
import static com.example.Kukey_Backend.global.entity.RequestStatus.NONE;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 실습실 개방 정보 조회
     */
    @Transactional(readOnly = true)
    public GetSpacesOpenInfoResponse getSpacesOpenInfo() {
        List<spaceOpenInfo> spaceList = spaceRepository.findAll().stream()
                .map(space -> {

                    String requestOrReservationStatus;
                    if (space.getOpenStatus().equals(OPEN)) {
                        requestOrReservationStatus = space.getReservationStatus().toString();
                    }
                    else {
                        requestOrReservationStatus =space.getOpenRequestStatus().toString();
                    }

                    return spaceOpenInfo.builder()
                        .spaceId(space.getSpaceId())
                        .buildingName(space.getBuildingName().getBuildingName())
                        .spaceDisplayName(space.getSpaceDisplayName())
                        .openStatus(space.getOpenStatus().toString())
                        .RequestOrReservationStatus(requestOrReservationStatus)
                        .build();
                })
                .collect(Collectors.toList());

        return GetSpacesOpenInfoResponse.of(spaceList);
    }

    /**
     * 매일 오후 10시마다 실슬십 일괄 잠금 처리
     */
    @Async
    public void spaceSetOpenStatusLock() {

        spaceRepository.findAll().forEach(space -> {
            space.setOpenStatus(LOCKED);
        });
    }

    /**
     * 매일 정각마다 내역확인 후 실습실 예약상태 변경
     * ING 상태인 Space 중 활성 예약이 없는 경우 NONE으로 복구
     */
    @Async
    public void spaceSetReservationStatusIng() {

        // 시작 시간 ≤ 현재 시간 < 종료 시간인 예약 조회
        List<Reservation> activeReservations = reservationRepository
                .findByReservationStartTimeLessThanEqualAndReservationEndTimeAfter(LocalTime.now());

        // 해당 예약의 Space를 ING로 변경
        Set<Space> spacesToIng = activeReservations.stream()
                .map(Reservation::getSpace)
                .collect(Collectors.toSet());
        spacesToIng.forEach(space -> space.setReservationStatus(ING));
        spaceRepository.saveAll(spacesToIng);

        // ING 상태인 Space 중 활성 예약이 없는 경우 NONE으로 복구
        List<Space> ingSpaces = spaceRepository.findByReservationStatus(ING);
        ingSpaces.forEach(space -> {
            boolean hasActiveReservation = reservationRepository
                    .existsBySpaceAndReservationStartTimeLessThanEqualAndReservationEndTimeAfter(
                            space,LocalTime.now(),LocalTime.now()
                    );
            if (!hasActiveReservation) {
                space.setReservationStatus(NONE);
            }
        });
        spaceRepository.saveAll(ingSpaces);

    }
}
