package com.js.ms.todo.global.config.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class SchedulerUtils {

    /**
     * JobDetail : Job을 실행시키기 위한 구체적인 정보를 가지고 있는 인스턴스입니다.
     * JobBuilder API를 통해 만들 수 있습니다.
     * Job에 대한 설명 Job의 ID 등을 설정할 수 있습니다.
     */
    @Bean
    public static JobDetail buildJobDetail() {
        return JobBuilder.newJob().ofType(NoticeJob.class)
                .storeDurably()
                .withIdentity("NoticeJob")
                .withDescription("할일 마감 알림")
                .build();
    }

    /**
     * Trigger : Trigger는 Job이 실행되는 실행 조건을 가지고 있는 인스턴스입니다.
     * TriggerBuilder API를 통해 만들 수 있습니다.
     */
    @Bean
    public static Trigger buildTrigger(@Qualifier("buildJobDetail") JobDetail job) {

        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("NoticeJobTrigger")
                // 0 0 4 * * ?
                .withSchedule(cronSchedule("* 30 * * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Seoul")))
                .build();
    }
}
