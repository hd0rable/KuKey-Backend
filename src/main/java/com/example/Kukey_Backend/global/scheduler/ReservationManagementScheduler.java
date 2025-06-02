package com.example.Kukey_Backend.global.scheduler;

import com.example.Kukey_Backend.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationManagementScheduler implements ApplicationRunner {

    private final ReservationService reservationService;

    //매일 오늘날짜 기준으로 완료된 예약내역 삭제
    @Scheduled(cron = "0 0 0 * * *")
    public void expiredReservationDelete() { reservationService.expiredReservationDelete(); }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
