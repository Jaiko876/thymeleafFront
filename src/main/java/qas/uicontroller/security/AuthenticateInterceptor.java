package qas.uicontroller.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import qas.uicontroller.security.model.ValidatedToken;
import qas.uicontroller.service.CookieService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticateInterceptor extends HandlerInterceptorAdapter {
    private CookieService cookieService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticateInterceptor(CookieService cookieService, JwtTokenProvider jwtTokenProvider) {
        this.cookieService = cookieService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        Cookie cookie = cookieService.getCookie(request);
        String token = null;
        if (cookie != null) {
            token = cookieService.getToken(cookie);
        }
        if (token != null) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ValidatedToken validatedToken = restTemplate.getForObject("http://localhost:8084/validate_token?token="
                        + token, ValidatedToken.class);
                if (validatedToken != null && validatedToken.isValidated()) {
                    UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else if (validatedToken != null && !validatedToken.isValidated()) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    throw new BadCredentialsException("token expired " + validatedToken.getMessage());
                }
            } catch (RestClientException e) {
                e.printStackTrace();
                request.setAttribute("err", e);
            }
        }
        return super.preHandle(request, response, handler);
    }
}
