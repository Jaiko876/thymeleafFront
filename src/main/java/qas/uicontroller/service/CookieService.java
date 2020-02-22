package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.service.IService.ICookieService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService implements ICookieService {

    @Override
    public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie sessionCookie;
        for (Cookie cookie : cookies) {
            if (("token").equals(cookie.getName())) {
                sessionCookie = cookie;
                return sessionCookie;
            }
        }
        return null;
    }

    @Override
    public String getToken(Cookie cookie) {
        if (cookie != null) {
           return cookie.getValue();
        }
        return null;
    }

    @Override
    public HttpEntity createEntityWithToken(HttpServletRequest request) {
        Cookie cookie = getCookie(request);
        String token = getToken(cookie);
        if (token != null) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            return new HttpEntity(headers);
        }
        return null;
    }

    @Override
    public HttpHeaders getBeaverHeader(Cookie cookie) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = getToken(cookie);
        httpHeaders.setBearerAuth(token);
        return httpHeaders;
    }
}
