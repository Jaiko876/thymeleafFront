package qas.uicontroller.controller;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.LoginForm;
import qas.uicontroller.service.CookieService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class LoginController {
    private CookieService cookieService;

    public LoginController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, HttpServletResponse response) {
        System.out.println(loginForm.getUsername());
        System.out.println(loginForm.getPassword());
        HttpEntity<LoginForm> request = new HttpEntity<>(loginForm);

        String token = new RestTemplate().postForObject( "http://localhost:8084/login", request , String.class );
        System.out.println(token);
        if (token != null) {
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "loggout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = cookieService.getCookie(request);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }

}