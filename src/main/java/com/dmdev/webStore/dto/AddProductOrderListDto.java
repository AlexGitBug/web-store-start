package com.dmdev.webStore.dto;

import lombok.Value;

import java.util.List;

@Value
public class AddProductOrderListDto {

    Integer orderId;
    List<Integer> productList;
}
