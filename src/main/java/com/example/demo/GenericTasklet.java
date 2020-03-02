package com.example.demo;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class GenericTasklet implements Tasklet {

    public int COUNT = 1;
    public String name;

    public GenericTasklet(final String name) {
        super();
        this.name = name;
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution,
            final ChunkContext chunkContext) throws Exception {
        System.out.println("Exec " + name + " Tasklet " + (COUNT++));

        // TODO comment this line for without error case
        contribution.setExitStatus(ExitStatus.FAILED);
        return RepeatStatus.FINISHED;
    }

}
