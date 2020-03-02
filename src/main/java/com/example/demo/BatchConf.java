package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConf {

    @Bean
    public Step chargementUrbainStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargementUrbainStep")
                .tasklet(new GenericTasklet("chargementUrbainStep"))
                .build();
    }

    @Bean
    public Step envoiDataWsStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("envoiDataWsStep")
                .tasklet(new GenericTasklet("envoiDataWsStep"))
                .build();
    }

    @Bean
    public Step moveOkFileStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("moveOkFileStep")
                .tasklet(new GenericTasklet("moveOkFileStep"))
                .build();
    }

    @Bean
    public Step chargementRuralStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargementRuralStep")
                .tasklet(new GenericTasklet("chargementRuralStep"))
                .build();
    }

    @Bean
    public Step moveKoFileStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("moveKoFileStep")
                .tasklet(new GenericTasklet("moveKoFileStep"))
                .build();
    }

    @Bean
    public Job momoJob(final JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("momoJob")
                .start(chargementUrbainStep(null))
                    .on("FAILED").to(moveKoFileStep(null))
                .from(chargementUrbainStep(null))
                    .on("*").to(envoiDataWsStep(null))
                .from(envoiDataWsStep(null))
                    .on("FAILED")
                    .to(moveKoFileStep(null))
                .from(envoiDataWsStep(null))
                    .on("*").to(moveOkFileStep(null))
                    .next(chargementRuralStep(null))
                .from(moveKoFileStep(null))
                    .on("*").to(chargementRuralStep(null))
                .end()
                .build();
    }
}
