package com.rig.authenticationservice;

import com.rig.authenticationservice.security.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public abstract class MockedTestWithSecurity extends MockedTest {
    
    protected Authentication authentication;
    
    protected SecurityContext securityContext;
    
    protected UserDetails userDetails;
    
    @Override
    @BeforeEach
    public void setup() {
        super.setup();

        this.userDetails = UserDetailsImpl.builder()
                .withUsername("test")
                .withPassword("test")
                .withAuthorities(new ArrayList<>())
                .build();
        this.authentication = Mockito.mock(Authentication.class);
        this.securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
    }
    
}
