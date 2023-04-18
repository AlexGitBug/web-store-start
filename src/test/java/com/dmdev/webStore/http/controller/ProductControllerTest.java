package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.integration.IntegrationTestBase;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.brand;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.catalogId;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.color;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.dateOfRelease;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.image;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.model;
import static com.dmdev.webStore.dto.product.ProductCreateEditDto.Fields.price;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class ProductControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ProductService productService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("product/products"),
                        model().attributeExists("products", "brands", "catalogs"),
                        model().attribute("catalogs", Matchers.containsInAnyOrder("APPLE", "SAMSUNG", "SONY" ))
                );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/products")
                        .param(model, "testModel")
                        .param(dateOfRelease, "2000-01-01")
                        .param(price, "123")
                        .param(image, "test")
                        .param(color, "BLACK")
                        .param(brand, "APPLE")
                        .param(catalogId, "1")

                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/products/*")
                );

    }

    @Test
    @SneakyThrows
    void findById() {
        var productReadDto = productService.create(new ProductCreateEditDto(
                "test-model",
                LocalDate.now(),
                123,
                "test-image",
                Color.BLACK,
                Brand.APPLE,
                1
        ));

        mockMvc.perform(get("/products/" + productReadDto.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("product/product"),
                        model().attributeExists("product"),
                        model().attribute("product", equalTo(productReadDto))
                );
    }

    @Test
    @SneakyThrows
    void update() {
        var productReadDto = productService.create(new ProductCreateEditDto(
                "test-model",
                LocalDate.now(),
                123,
                "test-image",
                Color.BLACK,
                Brand.APPLE,
                1
        ));

        mockMvc.perform(post("/products/" + productReadDto.getId() + "/update")
                        .param(model, "testModel")
                        .param(dateOfRelease, "2000-01-01")
                        .param(price, "123")
                        .param(image, "test")
                        .param(color, "BLACK")
                        .param(brand, "APPLE")
                        .param(catalogId, "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/products/" + productReadDto.getId())
                );
    }

    @Test
    @SneakyThrows
    void delete() {
        var productReadDto = productService.create(new ProductCreateEditDto(
                "test-model",
                LocalDate.now(),
                123,
                "test-image",
                Color.BLACK,
                Brand.APPLE,
                1
        ));

        mockMvc.perform(post("/products/" + productReadDto.getId() + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/products")
                );
    }

}
