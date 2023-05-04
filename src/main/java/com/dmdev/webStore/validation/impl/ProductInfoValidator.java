package com.dmdev.webStore.validation.impl;

import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.validation.ProductInfo;
import com.nimbusds.jose.util.IntegerUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductInfoValidator implements ConstraintValidator<ProductInfo, ProductCreateEditDto> {
    @Override
    public boolean isValid(ProductCreateEditDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getModel());

    }
}
