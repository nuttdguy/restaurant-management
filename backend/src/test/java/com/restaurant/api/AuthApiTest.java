package com.restaurant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.api.AuthApi;
import com.restaurant.domain.dto.request.TForgotPassword;
import com.restaurant.domain.dto.request.TLogin;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TResetPassword;
import com.restaurant.domain.dto.response.VwJwt;
import com.restaurant.domain.dto.response.VwUser;
import com.restaurant.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureWebMvc
class AuthApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    UserService userService;

//    @Test
    void testRegisterNewUser() throws Exception {
        TRegisterUser user = new TRegisterUser("username", "firstName", "lastName", "password", "password");
        // return the expected result from the dependency
        VwUser expected = new VwUser(UUID.randomUUID(), "username", "firstName", "lastName");
        when(userService.registerNewUser(any(), anyString())).thenReturn(expected);

        // perform the request
//        MvcResult result = mockMvc.perform(post(restTemplate.postForLocation("/auth/register", ))

//        Assertions.assertEquals(expected, result);
    }


}
