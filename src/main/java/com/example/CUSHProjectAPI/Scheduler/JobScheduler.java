package com.example.CUSHProjectAPI.Scheduler;

import com.example.CUSHProjectAPI.job.OldBoardJobConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
public class JobScheduler {

    private final JobLauncher jobLauncher;

    private final OldBoardJobConfiguration oldBoardJobConfiguration;

    @Scheduled(cron = "0 27 * * * *") //초 분 시 일 월 요일
    public void runJob() {

        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(oldBoardJobConfiguration.oldBoardJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
