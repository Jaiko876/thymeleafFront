package qas.uicontroller.service.IService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface ICookieService {
    String getToken(Cookie cookie);
    HttpEntity createEntityWithToken(HttpServletRequest request);
    Cookie getCookie(HttpServletRequest request);
    HttpHeaders getBeaverHeader(Cookie cookie);
}
