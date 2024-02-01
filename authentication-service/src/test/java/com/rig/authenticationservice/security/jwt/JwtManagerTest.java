package com.rig.authenticationservice.security.jwt;

import com.rig.authenticationservice.MockedTestWithSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerTest extends MockedTestWithSecurity {

    @InjectMocks
    private JwtManager jwtManager;

    @Override
    @BeforeEach
    public void setup() {
        super.setup();
        jwtManager.setExpirationMs(3600000);
        jwtManager.setSecret("B%{9Uyy1FnvfE2#1|U?lD31\\wwb2^g2(Iuc2S{km~9\\7R;.[5J`4@q5r8/{|o%H");
    }

    @Test
    void testGenerateJwt() {
        final String jwt = jwtManager.generateJwt(authentication);
        assertThat(jwt).isNotNull();
    }

}