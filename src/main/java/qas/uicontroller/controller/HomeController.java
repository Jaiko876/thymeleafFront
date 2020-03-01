package qas.uicontroller.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.security.JwtTokenProvider;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class HomeController {
    private CookieService cookieService;
    private JwtTokenProvider jwtTokenProvider;
    private UsersService usersService;

    public HomeController(CookieService cookieService, JwtTokenProvider jwtTokenProvider, UsersService usersService) {
        this.cookieService = cookieService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest httpServletRequest) throws Exception {
        Cookie cookie = cookieService.getCookie(httpServletRequest);
        String token = cookieService.getToken(cookie);
        int user_id = jwtTokenProvider.getId(token);
        UserWithoutPassword userById = usersService.getUserById(httpServletRequest, user_id);
        model.addAttribute("user", userById);
        return "home";
    }
}
