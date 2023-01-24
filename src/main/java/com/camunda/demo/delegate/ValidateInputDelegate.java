package com.camunda.demo.delegate;

import com.camunda.demo.config.ProcessConstants;
import com.camunda.demo.variables.InputData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component("validateInput")
@Slf4j
@RequiredArgsConstructor
public class ValidateInputDelegate implements JavaDelegate {

    private final Validator validator;

    public void execute(DelegateExecution execution) throws Exception {

        InputData input = execution.getVariableTyped(ProcessConstants.VAR_KEY_INPUT);

        Set<ConstraintViolation<InputData>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            String errorMessage = formatViolations(violations);
            log.error(errorMessage);
            throw new BpmnError(ProcessConstants.INVALID_INPUT_ERROR, errorMessage);
        }
    }

    private String formatViolations(Set<ConstraintViolation<InputData>> violations) {
        return "Invalid input-data provided: " + violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
