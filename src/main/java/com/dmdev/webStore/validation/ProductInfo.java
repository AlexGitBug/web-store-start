package com.dmdev.webStore.validation;

import com.dmdev.webStore.validation.impl.ProductInfoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = ProductInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductInfo {

    String message() default "Model should be filled in";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
