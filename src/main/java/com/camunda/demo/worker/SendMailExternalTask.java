package com.camunda.demo.worker;

import com.camunda.demo.config.ProcessConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ExternalTaskSubscription("sendMail")
@Slf4j
public class SendMailExternalTask implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        MailType mailType = externalTask.getVariable(ProcessConstants.VAR_KEY_MAIL_TYPE);

        switch (mailType) {
            case SUCCESS -> log.info("Sending Email, Suggestion is {}", getSuggestedConsole(externalTask));
            case REJECTION -> log.info("Sending Rejection-Email, no suggestion");
        }

        externalTaskService.complete(externalTask);
    }

    private enum MailType {
        SUCCESS,
        REJECTION
    }

    private String getSuggestedConsole(ExternalTask externalTask) {

        String suggestion = externalTask.getVariable(ProcessConstants.VAR_KEY_SUGGESTION);
        if (StringUtils.isBlank(suggestion)) {
            List<String> suggestions = externalTask.getVariableTyped(ProcessConstants.VAR_KEY_SUGGESTIONS);
            return suggestions.get(0);
        }

        return suggestion;

    }
}
