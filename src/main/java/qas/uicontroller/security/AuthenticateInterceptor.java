package qas.uicontroller.security;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import qas.uicontroller.security.model.ValidatedToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticateInterceptor extends HandlerInterceptorAdapter {
    private Cookie sessionCookie;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        Cookie[] cookies = request.getCookies();
        if( cookies == null || cookies.length < 1 ) {
            response.sendRedirect("login");
            return false;
        }

        sessionCookie = null;
        for(Cookie cookie : cookies) {
            if(( "token" ).equals(cookie.getName())) {
                sessionCookie = cookie;
                break;
            }
        }

        if( sessionCookie == null || StringUtils.isEmpty( sessionCookie.getValue() ) ) {
            response.sendRedirect("login");
            return false;
        }
        String token = sessionCookie.getValue();
        RestTemplate restTemplate = new RestTemplate();
        ValidatedToken validatedToken = restTemplate.getForObject("http://localhost:8084/validate_token?token="
                + token, ValidatedToken.class);

        if (validatedToken != null && !validatedToken.isValidated()) {
            sessionCookie.setMaxAge(0);
            response.addCookie(sessionCookie);
            response.sendRedirect("login");
            return false;
        }


        return super.preHandle(request, response, handler);
    }
}
