package com.icici.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerTestClass {


    @Scheduled(fixedRate = 30000)
    private void test(){
        log.info("Scheduler method has invoked CICD: Hi Im Up And Running....");
    }
}
