package com.dmdev.webStore.dto.catalog;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CatalogCreateEditDto {

    String category;
}
