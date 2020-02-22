package qas.uicontroller.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import qas.uicontroller.service.CookieService;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtTokenProvider jwtTokenProvider;
    private CookieService cookieService;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, CookieService cookieService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieService = cookieService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtRequestFilter jwtTokenFilter = new JwtRequestFilter(jwtTokenProvider, cookieService);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
