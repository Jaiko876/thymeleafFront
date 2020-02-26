package qas.uicontroller.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.CookieService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {
    private CookieService cookieService;

    public UsersController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @RequestMapping(value = "allusers", method = RequestMethod.GET)
    public String showUsers(Model model, HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<UserWithoutPassword[]> usersArray = new RestTemplate().exchange("http://localhost:8080/user",
                HttpMethod.GET, entityWithToken, UserWithoutPassword[].class);

        if (usersArray.getBody() == null) {
            throw new Exception("Пустой RestTemplate");
        }

        List<UserWithoutPassword> listUsers = Arrays.asList(usersArray.getBody());
        //System.out.println(listUsers.toString());
        model.addAttribute("listusers", listUsers);
        return "users/showUsers";

    }

    @RequestMapping(value = "showroles", method = RequestMethod.GET)
    public String showRoles(Model model, HttpServletRequest request) throws Exception {
        String id =request.getParameter("id");
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<Role[]> roleArray=new RestTemplate().exchange("http://localhost:8080/user/{id}/role",
                HttpMethod.GET, entityWithToken, Role[].class,id);
        if (roleArray.getBody() == null) {
            throw new Exception("Пустой RestTemplate");
        }
        List<Role> listroles=Arrays.asList(roleArray.getBody());
        //System.out.println(listroles.toString());
        model.addAttribute("id", id);
        model.addAttribute("listroles",listroles);
        return "users/showUserRoles";
    }

}
