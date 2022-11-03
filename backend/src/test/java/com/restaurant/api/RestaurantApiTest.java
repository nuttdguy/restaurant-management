package com.restaurant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebMvc
class RestaurantApiTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean;
    @Autowired RestaurantApi restaurantApi;
    @Autowired RestaurantService restaurantService;

    private static final UUID restaurantId = UUID.randomUUID();
    private static final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(restaurantApi)
                .setConversionService(Objects.requireNonNull(formattingConversionServiceFactoryBean.getObject()))
                .build();

    }

    @Test
    void tempTest() {
        // do to do
        assert(true);
    }

}
