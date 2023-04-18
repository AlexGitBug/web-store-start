package com.dmdev.webStore.repository.filter;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CatalogFilter {

    String category;
}