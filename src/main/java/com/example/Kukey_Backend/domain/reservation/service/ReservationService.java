package com.example.Kukey_Backend.domain.reservation.service;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import com.example.Kukey_Backend.domain.reservation.domain.dto.request.PostReservationToSpaceRequest;
import com.example.Kukey_Backend.domain.reservation.domain.dto.response.*;
import com.example.Kukey_Backend.domain.reservation.domain.repository.ReservationRepository;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.Kukey_Backend.global.entity.RequestStatus.APPROVED;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;
    private final JwtUtil jwtUtil;

    /**
     * 실습실 예약하기
     */
    public Void reservationToSpace(Long spaceId, PostReservationToSpaceRequest postReservationToSpaceRequest,String authHeader) {

        LocalTime allowedStart = LocalTime.of(9, 0); // 오전 9시
        LocalTime allowedEnd = LocalTime.of(22, 0); // 오후 10시

        //과거 날짜 예약 차단
        if (postReservationToSpaceRequest.reservationDate().isBefore(LocalDate.now())) {
            throw new GlobalException(RESERVATION_DATE_INVALID);
        }


        //오늘 날짜인 경우, 현재 시간 이후만 예약 가능
        if (postReservationToSpaceRequest.reservationDate().isEqual(LocalDate.now())) {
            if (postReservationToSpaceRequest.reservationStartTime().isBefore(LocalTime.now())) {
                throw new GlobalException(RESERVATION_DATE_TIME_INVALID);
            }
        }

        // 예약 시간이 허용 범위(09:00~22:00)를 벗어나는 경우 예외
        if (postReservationToSpaceRequest.reservationStartTime().isBefore(allowedStart)) {
            throw new GlobalException(RESERVATION_TIME_INVALID);
        }
        if (postReservationToSpaceRequest.reservationEndTime().isAfter(allowedEnd)) {
            throw new GlobalException(RESERVATION_TIME_INVALID);
        }

        // 예약 시간 3시간 이상 예외처리
        Duration duration = Duration.between(postReservationToSpaceRequest.reservationStartTime(), postReservationToSpaceRequest.reservationEndTime());
        if (duration.toHours() > 3 || duration.isNegative() || duration.isZero()) {
            throw new GlobalException(RESERVATION_TIME_TOO_LONG);
        }

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new GlobalException(CANNOT_FOUND_SPACE));

        // 예약 시간 겹침 예외처리
        List<Reservation> existingReservations = reservationRepository
                .findAllBySpaceAndReservationDate(space,postReservationToSpaceRequest.reservationDate());

        boolean isOverlap = existingReservations.stream().anyMatch(res ->
                !(postReservationToSpaceRequest.reservationEndTime().isBefore(res.getReservationStartTime()) ||
                        postReservationToSpaceRequest.reservationStartTime().isAfter(res.getReservationEndTime()))
        );
        if (isOverlap) {
            throw new GlobalException(DUPLICATE_RESERVATION_TIME);
        }


        String token = jwtUtil.extractToken(authHeader);

        Reservation reservation = Reservation.builder()
                .space(space)
                .reservationDate(postReservationToSpaceRequest.reservationDate())
                .reservationStartTime(postReservationToSpaceRequest.reservationStartTime())
                .reservationEndTime(postReservationToSpaceRequest.reservationEndTime())
                .studentNumber(postReservationToSpaceRequest.studentNumber())
                .studentName(postReservationToSpaceRequest.studentName())
                .studentEmail(jwtUtil.extractEmail(token))
                .studentGroup(postReservationToSpaceRequest.studentGroup())
                .reservationPurpose(postReservationToSpaceRequest.reservationPurpose())
                .status(APPROVED)
                .build();

        reservationRepository.save(reservation);
        return null;
    }

    /**
     * 실습실 학번으로 예약 조회
     */
    public GetReservationInfoResponse getReservationInfo(String studentNumber, String studentName) {

        // 학번과 이름으로 예약 목록 조회
        List<Reservation> reservations = reservationRepository.findByStudentNumberAndStudentNameOrderByReservationDateAsc(studentNumber, studentName);

        List<ReservationInfo> reservationInfoList = reservations.stream()
                .map(reservation -> ReservationInfo.builder()
                        .spaceId(reservation.getSpace().getSpaceId())
                        .buildingName(reservation.getSpace().getBuildingName().getBuildingName())
                        .spaceDisplayName(reservation.getSpace().getSpaceDisplayName())
                        .reservationId(reservation.getReservationId())
                        .reservationDate(reservation.getReservationDate())
                        .reservationStartTime(reservation.getReservationStartTime())
                        .reservationEndTime(reservation.getReservationEndTime())
                        .studentGroup(reservation.getStudentGroup())
                        .reservationPurpose(reservation.getReservationPurpose())
                        .build())
                .toList();

        return GetReservationInfoResponse.builder()
                .studentNumber(studentNumber)
                .studentName(studentName)
                .reservationList(reservationInfoList)
                .build();
    }

    /**
     * 예약 취소하기
     */
    public Void deleteReservation(Long reservationId, String authHeader) {

        Reservation reservation =reservationRepository.findById(reservationId).orElseThrow
                (() -> new GlobalException(CANNOT_FOUND_RESERVATION));

        String token = jwtUtil.extractToken(authHeader);

        if(! jwtUtil.extractEmail(token).equals(reservation.getStudentEmail())) {
            throw new GlobalException(CANNOT_DELETE_RESERVATION);
        }


        reservationRepository.deleteById(reservationId);

        return null;
    }

    /**
     * 매일 0시 마다 지난 예약 일괄삭제
     */
    @Async
    public void expiredReservationDelete() {

        // 어제까지의 모든 예약 삭제
        List<Reservation> expiredReservations = reservationRepository.findByReservationDateBefore(LocalDate.now());
        reservationRepository.deleteAll(expiredReservations);
    }

    /**
     * 실습실 별 예약 현황 조회
     */
    @Transactional(readOnly = true)
    public GetSpacesReservationInfoResponse getSpacesReservationInfo(LocalDate date) {
        // 모든 공간 조회
        List<Space> spaces = spaceRepository.findAll();

        // 각 공간별로 예약 불가 시간대 매핑
        List<SpaceReservationInfo> reservationInfos = spaces.stream()
                .map(space -> {
                    // 날짜 기준으로 예약 조회
                    List<Reservation> reservations = reservationRepository
                            .findBySpaceAndReservationDate(space, date);

                    //예약 정보를 TimeSlot 리스트로 변환
                    List<TimeSlot> timeSlots = reservations.stream()
                            .map(reservation -> TimeSlot.builder()
                                    .startTime(reservation.getReservationStartTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                                    .endTime(reservation.getReservationEndTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                                    .build())
                            .collect(Collectors.toList());

                    //SpaceReservationInfo 객체 생성
                    return SpaceReservationInfo.builder()
                            .spaceId(space.getSpaceId())
                            .buildingName(space.getBuildingName().getBuildingName())
                            .spaceDisplayName(space.getSpaceDisplayName())
                            .unavailableReservationTimeList(timeSlots)
                            .build();
                })
                .collect(Collectors.toList());

        return GetSpacesReservationInfoResponse.builder()
                .reservationList(reservationInfos)
                .build();
    }

}
