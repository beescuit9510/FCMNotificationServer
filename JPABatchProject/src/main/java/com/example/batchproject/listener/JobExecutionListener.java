package com.example.batchproject.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobExecutionListener implements org.springframework.batch.core.JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus()==BatchStatus.COMPLETED) {

        } else if (jobExecution.getStatus()==BatchStatus.FAILED) {

        }
    }
}
