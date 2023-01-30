package com.camunda.demo;

import com.camunda.demo.config.ProcessConstants;
import com.camunda.demo.variables.InputData;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.spin.Spin;
import org.camunda.spin.json.SpinJsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@ExtendWith(ProcessEngineExtension.class)
public class DecisionTest {

    ProcessEngine processEngine;

    @BeforeEach
    void deploy() {
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .unlimitedList();

        if (CollectionUtils.isEmpty(deployments)) {
            Deployment deployment = processEngine.getRepositoryService()
                    .createDeployment()
                    .addClasspathResource("console-suggestions.dmn")
                    .deploy();
            log.info("##### Deployment with ID {} done", deployment.getId());
        }
    }

    @Test
    void testDeployment() {
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .unlimitedList();

        Assertions.assertThat(deployments)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @Disabled("Test does not work, there seems to be a problem with the input as variable")
    void evaluteDecision() {
        assertSingleDeployment();

        var decisionService = processEngine.getDecisionService();
        SpinJsonNode json = Spin.JSON(getInputData());

        DmnDecisionTableResult result = decisionService.evaluateDecisionTableByKey(
                "Consosle_Suggestion_ID",
                Map.of(ProcessConstants.VAR_KEY_INPUT, json)
        );

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getResultList())
                .hasSize(3);
    }

    private InputData getInputData() {
        return InputData.builder()
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .email("test@test.de")
                .build();
    }

    private void assertSingleDeployment() {
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .unlimitedList();

        Assertions.assertThat(deployments)
                .isNotNull()
                .hasSize(1);
    }
}
