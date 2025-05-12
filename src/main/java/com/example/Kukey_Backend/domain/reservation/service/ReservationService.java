package com.example.Kukey_Backend.domain.reservation.service;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import com.example.Kukey_Backend.domain.reservation.domain.dto.request.PostReservationToSpaceRequest;
import com.example.Kukey_Backend.domain.reservation.domain.dto.response.GetReservationInfoResponse;
import com.example.Kukey_Backend.domain.reservation.domain.dto.response.ReservationInfo;
import com.example.Kukey_Backend.domain.reservation.domain.repository.ReservationRepository;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.domain.space.domain.repository.SpaceRepository;
import com.example.Kukey_Backend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.Kukey_Backend.global.entity.RequestStatus.APPROVED;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 실습실 예약하기
     */
    public Void reservationToSpace(Long spaceId, PostReservationToSpaceRequest postReservationToSpaceRequest) {

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

        Reservation reservation = Reservation.builder()
                .space(space)
                .reservationDate(postReservationToSpaceRequest.reservationDate())
                .reservationStartTime(postReservationToSpaceRequest.reservationStartTime())
                .reservationEndTime(postReservationToSpaceRequest.reservationEndTime())
                .studentNumber(postReservationToSpaceRequest.studentNumber())
                .studentName(postReservationToSpaceRequest.studentName())
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
    public Void deleteReservation(Long reservationId) {

        reservationRepository.findById(reservationId).orElseThrow
                (() -> new GlobalException(CANNOT_FOUND_RESERVATION));

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
}
