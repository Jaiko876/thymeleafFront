package qas.uicontroller.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest httpServletRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie sessionCookie = null;
        for(Cookie cookie : cookies) {
            if(( "token" ).equals(cookie.getName())) {
                sessionCookie = cookie;
                break;
            }
        }
        headers.setBearerAuth(sessionCookie.getValue());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Role> response = restTemplate.exchange(
                "http://localhost:8080/role", HttpMethod.GET, entity , Role.class);
        System.out.println(response.toString());
        return "home/home";
    }
}
