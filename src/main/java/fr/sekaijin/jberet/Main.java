package fr.sekaijin.jberet;

import java.util.Properties;

import javax.batch.runtime.JobExecution;
import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main implements QuarkusApplication {
    @Inject
    QuarkusJobOperator jobOperator;
    
    void start() {
        Job job = new JobBuilder("programmatic")
                .step(new StepBuilder("programmaticStep")
                        .batchlet("programmaticBatchlet")
                        .build())
                .build();
        
        long executionId = jobOperator.start(job, new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
    }
    @Override
    public int run(String... args) throws Exception {
        start();
        return 0;
    }
    
}
