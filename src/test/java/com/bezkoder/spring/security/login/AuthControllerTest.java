package com.bezkoder.spring.security.login;

import com.bezkoder.spring.security.login.controllers.AuthController;
import com.bezkoder.spring.security.login.payload.request.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.util.logging.Logger;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ ObjectMapper.class, AuthController.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    static Logger log = Logger.getLogger(AuthControllerTest.class.getName());

    // user authentication
    private static String jwt; // can use this for your next test request
    @Test
    @Order(1)
    @DisplayName("User Authentication token")
    void authenticationTest() throws JsonProcessingException, Exception {
        final String link = "/api/auth/signin";
        LoginRequest defaultAuth = new LoginRequest("mod", "123456");

        System.out.println(objectMapper.writeValueAsString(defaultAuth));
        // perform the request
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post(link)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(defaultAuth)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
       // String c = result.getResponse().getCookies().toString() ;
        MockHttpServletResponse m = result.getResponse();
        String c = result.getResponse().getCookies()[0].getValue();
        String response = result.getResponse().getContentAsString();
        System.out.println("from response: " + response); //
        log.info("Fom response: "+response);

       /* JsonNode root = objectMapper.readTree(cookie);
        JsonNode jwtvalue = root.get("jwt");
        jwt = jwtvalue.textValue();
        System.out.println("jwt deserlized: " + jwt); */

    }

}