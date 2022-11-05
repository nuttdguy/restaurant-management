package com.restaurant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.response.VwUser;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
