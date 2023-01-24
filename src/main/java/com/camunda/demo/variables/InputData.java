package com.camunda.demo.variables;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@RequiredArgsConstructor
@Builder
@With
public class InputData {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    LocalDate birthday;

    @Email
    String email;
}
