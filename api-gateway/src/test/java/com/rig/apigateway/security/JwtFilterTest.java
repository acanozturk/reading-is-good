package com.rig.apigateway.security;

import com.rig.apigateway.MockedTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class JwtFilterTest extends MockedTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Override
    @BeforeEach
    public void setup() {
        super.setup();
        jwtFilter.setSecret("B%{9Uyy1FnvfE2#1|U?lD31\\wwb2^g2(Iuc2S{km~9\\7R;.[5J`4@q5r8/{|o%H");
    }

}