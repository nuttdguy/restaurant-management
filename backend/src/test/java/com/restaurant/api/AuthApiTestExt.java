package com.restaurant.api;

import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.mockito.Mockito.when;

//@WebMvcTest(AuthApi.class)
public class AuthApiTestExt {

    @Autowired
    private MockMvc mockMvc; // required for performing request / response

    // create a mock bean for each dependency
    @MockBean
    private UserService userService;

//    @Test
    public void resisterNewUserTest() {
        TRegisterUser tRegisterUser = new TRegisterUser(
                "username@test.com",
                "firstName",
                "lastName",
                "password",
                "password");

//        String verifyURL = format("http://%s:%s%s%s",
//                mockMvc.getDispatcherServlet().getServletName(),
//                mockMvc.getDispatcherServlet().getP,
//                mockMvc.getDispatcherServlet().getContextPath(),
//                "/auth/verify")
//        when(userService.registerNewUser(tRegisterUser)).thenReturn
    }

}
