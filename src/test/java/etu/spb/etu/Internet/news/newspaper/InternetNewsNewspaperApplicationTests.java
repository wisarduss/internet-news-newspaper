package etu.spb.etu.Internet.news.newspaper;

import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class InternetNewsNewspaperApplicationTests {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @Test
    void contextLoads() {
    }

}
