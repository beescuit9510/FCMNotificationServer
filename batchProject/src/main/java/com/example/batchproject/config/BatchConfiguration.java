package com.example.batchproject.config;

import com.example.batchproject.firebase.FirebaseCloudMessageService;
import com.example.batchproject.listener.BatchJobExecutionListener;
import com.example.batchproject.model.vo.StepDataBean;
import com.example.batchproject.tasklet.reader.MessageReader;
import com.example.batchproject.tasklet.sender.MessageSender;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchJobExecutionListener jobListener;



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
        return stepBuilderFactory.get("readMessage")
                .tasklet(messageReader())
                .build();
    }

    @Bean
    public Step sendMessage() {
        return stepBuilderFactory.get("sendMessage")
                .tasklet(messageSender())
                .build();
    }


    @Bean
    public Tasklet messageReader() {
        return new MessageReader();
    }

    @Bean
    public Tasklet messageSender() {
        return new MessageSender();
    }

    @Bean
    public StepDataBean stepDataBean() {
        return new StepDataBean();
    }

    @Bean
    public FirebaseCloudMessageService firebaseCloudMessageService(){
        return FirebaseCloudMessageService.createFirebaseCloudMessageService();
    }

}
