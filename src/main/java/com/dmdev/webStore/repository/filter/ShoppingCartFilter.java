package com.dmdev.webStore.repository.filter;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ShoppingCartFilter {

    LocalDate createdAt;
}