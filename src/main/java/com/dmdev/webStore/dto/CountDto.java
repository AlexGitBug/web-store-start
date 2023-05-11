package com.dmdev.webStore.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
public class CountDto {
    @NotNull
    @Positive
    Integer count14;
}
