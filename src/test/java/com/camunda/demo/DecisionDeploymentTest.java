package com.camunda.demo;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@Slf4j
@ExtendWith(ProcessEngineExtension.class)
public class DecisionDeploymentTest {

    ProcessEngine processEngine;

    @Test
    @org.camunda.bpm.engine.test.Deployment(resources = "console-suggestions.dmn")
    @Disabled("does not work!")
    void testDeployment() {

        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .unlimitedList();

        Assertions.assertThat(deployments)
                .isNotNull()
                .hasSize(1);
    }
}
