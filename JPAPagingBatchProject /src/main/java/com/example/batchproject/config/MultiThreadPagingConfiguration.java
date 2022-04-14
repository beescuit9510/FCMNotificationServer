package com.example.batchproject.config;

import com.example.batchproject.firebase.FCMService;
import com.example.batchproject.model.vo.PushMessage;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class MultiThreadPagingConfiguration {

    public static final String JOB_NAME = "multiThreadPagingBatch";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final FCMService fcmService;
    private final DataSource dataSource;

    private int chunkSize = 15;

    private int poolSize = 10;

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .preventRestart()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<PushMessage, PushMessage>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(executor())
                .throttleLimit(poolSize)
                .build();
    }


    @Bean
    public JpaPagingItemReader<PushMessage> reader() {

        Map<String, Object> params = new HashMap<>();
        params.put("isSent", 0);

        return new JpaPagingItemReaderBuilder<PushMessage>()
                .name(JOB_NAME + "_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString(" SELECT p FROM PushMessage p WHERE p.isSent =:isSent ")
                .parameterValues(params)
                .saveState(false)
                .build();
    }

    private ItemProcessor<PushMessage, PushMessage> processor() {

        return pushMessage -> {

            try {

                String response = fcmService.sendMessageTo(pushMessage);

                if(response!= null){

                    int isSentTrue = 1;

                    pushMessage.setIsSent(isSentTrue);
                }

            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }

            return pushMessage;
        };
    }


    @Bean
    public JdbcBatchItemWriter<PushMessage> writer() {

        return new JdbcBatchItemWriterBuilder<PushMessage>()
                .dataSource(dataSource)
                .sql("update push_message set is_sent = :isSent where push_message_no = :pushMessageNo")
                .beanMapped()
                .build();
    }
}