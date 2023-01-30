package com.camunda.demo.controller;

import com.camunda.demo.config.ProcessConstants;
import com.camunda.demo.controller.dto.ProcessInstanceResponse;
import com.camunda.demo.controller.dto.StartProcessRequest;
import com.camunda.demo.variables.InputData;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProcessRestController {

    private final RuntimeService runtimeService;

    @PostMapping("console-suggestion/start")
    public ResponseEntity<ProcessInstanceResponse> startConsoleSuggestion(
            @RequestBody @Validated StartProcessRequest request) {

        InputData input = InputData.builder()
                .name(request.getName())
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .build();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                ProcessConstants.CONSOLE_SUGGESTION_PROCESS_ID,
                Map.of(ProcessConstants.VAR_KEY_INPUT, input)
        );

        return ResponseEntity.accepted()
                .body(
                        new ProcessInstanceResponse(
                                processInstance.getProcessInstanceId(),
                                processInstance.getProcessDefinitionId()
                        )
                );
    }
}
