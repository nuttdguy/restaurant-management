package com.restaurant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.response.VwLink;
import com.restaurant.domain.dto.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@AutoConfigureMockMvc
class AuthApiTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    // user for test
    TRegisterUser tRegisterUser = TRegisterUser.builder()
            .username("alice@restaurant.com")
            .firstName("alice")
            .lastName("jane")
            .password("password")
            .confirmPassword("password")
            .authorities(Set.of(RoleType.PUBLIC_USER))
            .build();

    @Autowired
    public AuthApiTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


//    @Test
    void testRegisterNewUser() throws Exception {
        String verifyURL = "http::/localhost:9001/v1/api/auth/verify";
        VwLink vwLink = new VwLink(verifyURL);

    }


}
