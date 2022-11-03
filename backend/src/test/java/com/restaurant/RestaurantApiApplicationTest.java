package com.restaurant;

import com.restaurant.api.AuthApi;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestaurantApiApplicationTest {

    @Autowired AuthApi authApi;

    @Test
    void contextLoads() {
        // test to verify spring context loads
        assertThat(authApi).isNotNull();
    }
}
