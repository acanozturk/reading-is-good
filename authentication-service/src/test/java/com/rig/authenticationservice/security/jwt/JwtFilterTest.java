package com.rig.authenticationservice.security.jwt;

import com.rig.authenticationservice.MockedTest;
import com.rig.authenticationservice.security.jwt.JwtFilter;
import com.rig.authenticationservice.security.jwt.JwtManager;
import com.rig.authenticationservice.security.userdetails.UserDetailsImpl;
import com.rig.authenticationservice.security.userdetails.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class JwtFilterTest extends MockedTest {

    @Mock
    private JwtManager jwtManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Test
    void testDoFilterInternal_ValidJwt() throws ServletException, IOException {
        final String jwt = "validJwtToken";
        final UserDetails userDetails = UserDetailsImpl.builder()
                .withUsername("test")
                .withPassword("test")
                .withAuthorities(new ArrayList<>())
                .build();
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtManager.isValidJwt(jwt)).thenReturn(true);
        when(jwtManager.extractUsername(jwt)).thenReturn("test");
        when(userDetailsService.loadUserByUsername("test")).thenReturn(userDetails);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(request, times(1)).getHeader("Authorization");
        verify(jwtManager, times(1)).isValidJwt(jwt);
        verify(jwtManager, times(1)).extractUsername(jwt);
        verify(userDetailsService, times(1)).loadUserByUsername("test");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidJwt() throws ServletException, IOException {
        final String jwt = "invalidJwtToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtManager.isValidJwt(jwt)).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(request, times(1)).getHeader("Authorization");
        verify(jwtManager, times(1)).isValidJwt(jwt);
        verifyNoInteractions(userDetailsService);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(request, times(1)).getHeader("Authorization");
        verifyNoInteractions(jwtManager);
        verifyNoInteractions(userDetailsService);
        verify(filterChain, times(1)).doFilter(request, response);
    }

}