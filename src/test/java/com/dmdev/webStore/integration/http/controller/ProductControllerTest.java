package com.dmdev.webStore.integration.http.controller;

import com.dmdev.webStore.dto.ProductCreateEditDto;
import com.dmdev.webStore.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.brand;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.catalogId;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.color;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.dateOfRelease;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.image;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.model;
import static com.dmdev.webStore.dto.ProductCreateEditDto.Fields.price;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class ProductControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("product/products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(12)));
    }

    @Test
    void create() throws Exception {
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

}