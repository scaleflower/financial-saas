package com.fs.common.listener;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TaskListener implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        log.info("Handling job: {}, type: {}", job.getKey(), job.getType());

        Map<String, Object> variables = job.getVariablesAsMap();
        
        log.info("Job variables: {}", variables);

        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .join();
    }
}
