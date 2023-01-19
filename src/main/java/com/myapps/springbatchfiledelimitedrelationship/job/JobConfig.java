package com.myapps.springbatchfiledelimitedrelationship.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    public JobConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    /*
     * The .incrementer(new RunIdIncrementer()) option allows the job to run everytime the application
     * is started.
     * A job using this building option, can't be restarted if some error occurs.
     * If there's saved metadata in the job repository before the first time it runs with this option,
     * the job will not be executed, so the metadata must be cleared. Empty all the metadata tables.
     * If this option is not used, the application needs to be run at least one argument
     * whose value has not yet been used in a successful run, otherwise the application will not
     * run the job. But it's recovery (i.e. restart job from where it stopped on error) and overall
     * job execution management keeps working.
     */
    @Bean
    public Job job(
            Step step
    ) {
        return jobBuilderFactory
                .get("job")
                .start(step)
                .incrementer(new RunIdIncrementer()) // See comment above
                .build();
    }

}
