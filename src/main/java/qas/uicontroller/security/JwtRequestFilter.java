package qas.uicontroller.security;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;
import qas.uicontroller.security.model.ValidatedToken;
import qas.uicontroller.service.CookieService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JwtRequestFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;
    private RestTemplate restTemplate = new RestTemplate();
    private CookieService cookieService;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, CookieService cookieService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieService = cookieService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        System.out.println(httpServletRequest.getRequestURI());
        Cookie cookie = cookieService.getCookie(httpServletRequest);
        String token = null;
        if (cookie != null) {
            token = cookieService.getToken(cookie);
        }
        if (token != null) {
            try {
                ValidatedToken validatedToken = restTemplate.getForObject("http://localhost:8084/validate_token?token="
                        + token, ValidatedToken.class);

                if (validatedToken != null && validatedToken.isValidated()) {
                    UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else if (validatedToken != null && !validatedToken.isValidated()) {
                    cookie.setMaxAge(0);
                    HttpServletResponse response = (HttpServletResponse) servletResponse;
                    response.addCookie(cookie);
                    throw new JwtException("Время Вашего сеанса истекло. Пожалуйста, войдите в систему еще раз.");
                }
            } catch (RestClientException e) {
                e.printStackTrace();
                servletRequest.setAttribute("err", e);
            }
            catch (JwtException e) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                HttpSession session = httpServletRequest.getSession();

                session.setAttribute("jwtErr", true);
                response.sendRedirect("/login");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
