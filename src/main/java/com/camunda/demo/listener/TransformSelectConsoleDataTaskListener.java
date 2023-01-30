package com.camunda.demo.listener;

import com.camunda.demo.config.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransformSelectConsoleDataTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        List<String> suggestionList = (List<String>) delegateTask.getVariableTyped(ProcessConstants.VAR_KEY_SUGGESTIONLIST, true).getValue();

        var selectInputs = suggestionList.stream()
                .map(s -> new SelectInput(s, s))
                .toList();

        delegateTask.setVariableLocal(ProcessConstants.VAR_KEY_SUGGESTIONS, selectInputs);
    }

    record SelectInput(String label, String value) {
    }

}
