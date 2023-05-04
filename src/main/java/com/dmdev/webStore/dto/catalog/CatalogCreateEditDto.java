package com.dmdev.webStore.dto.catalog;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class CatalogCreateEditDto {

    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 80)
    String category;
}
