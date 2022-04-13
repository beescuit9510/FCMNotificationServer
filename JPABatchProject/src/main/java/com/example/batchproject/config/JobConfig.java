package com.example.batchproject.config;

import com.example.batchproject.listener.JobExecutionListener;
import com.example.batchproject.tasklet.reader.MessageReader;
import com.example.batchproject.tasklet.sender.MultithreadMessageTrigger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@EnableJpaRepositories("com.example.batchproject.model.repository")
@Configuration
public class JobConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private JobExecutionListener jobListener;
    private MessageReader reader;
    private MultithreadMessageTrigger sender;

    @Autowired
    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobExecutionListener jobListener,MessageReader reader, MultithreadMessageTrigger sender) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobListener = jobListener;
        this.reader = reader;
        this.sender = sender;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .listener(jobListener)
                .start(readMessage())
                .next(sendMessage())
                .build();
    }


    @Bean
    public Step readMessage() {
        FlatFileItemReader a ;
        return stepBuilderFactory.get("readMessage")
                .tasklet(reader)
                .build();
    }

    @Bean
    public Step sendMessage() {
        return stepBuilderFactory.get("sendMessage")
                .tasklet(sender)
                .build();
    }






}
