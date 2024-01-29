package com.rig.authenticationservice.security.jwt;

import com.rig.authenticationservice.MockedTestWithSecurity;
import com.rig.authenticationservice.security.jwt.JwtManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void testExtractUsername() {
        final String jwt = jwtManager.generateJwt(authentication);
        final String username = jwtManager.extractUsername(jwt);

        assertThat(username).isEqualTo("test");
    }

    @Test
    void testIsValidJwt() {
        final String jwt = jwtManager.generateJwt(authentication);
        final boolean isValid = jwtManager.isValidJwt(jwt);

        assertThat(isValid).isTrue();
    }

    @Test
    void testIsValidJwtWithExpiredJwt() {
        jwtManager.setExpirationMs(0);

        final String jwt = jwtManager.generateJwt(authentication);

        assertThatThrownBy(() -> jwtManager.isValidJwt(jwt))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void testIsValidJwtWithInvalidJwt() {
        assertThatThrownBy(() -> jwtManager.isValidJwt("invalidjwt"))
                .isInstanceOf(MalformedJwtException.class);
    }

}