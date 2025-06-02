package com.example.Kukey_Backend.global.scheduler;

import com.example.Kukey_Backend.domain.space.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpaceManagementScheduler implements ApplicationRunner {

    private final SpaceService spaceService;

    //매일 오후 10시마다 실습실 자동 작금처리
    @Scheduled(cron = "0 0 22 * * *")
    public void spaceSetOpenStatusLock() { spaceService.spaceSetOpenStatusLock(); }

    //매일 오전9시부터 오후 10시까지 정각마다 예약 내역 확인후 실습실 예약상태 ING으로 변경
    @Scheduled(cron = "0 0 9-22 * * *")
    public void spaceSetReservationStatusIng(){ spaceService.spaceSetReservationStatusIng(); }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
