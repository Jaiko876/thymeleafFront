package qas.uicontroller.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.RoleService;
import qas.uicontroller.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {
    private CookieService cookieService;
    private UsersService usersService;
    private RoleService roleService;

    public UsersController(CookieService cookieService, UsersService usersService, RoleService roleService) {
        this.cookieService = cookieService;
        this.usersService = usersService;
        this.roleService = roleService;
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
        String buttonStatus = "true";
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<Role[]> roleArray = new RestTemplate().exchange("http://localhost:8080/user/{id}/role",
                HttpMethod.GET, entityWithToken, Role[].class, id);
        if (roleArray.getStatusCode().isError()) {
            throw new Exception(roleArray.getStatusCode().getReasonPhrase());
        }
        List<Role> listroles = Arrays.asList(roleArray.getBody());
        List<Role> allRoles = roleService.getAllRoles(request);
        List<Role> filterRoles = roleService.filterRoles(listroles, allRoles);
        if (filterRoles.isEmpty()) {
            buttonStatus = "";
        }
        UserWithoutPassword userById = usersService.getUserById(request, Integer.parseInt(id));

        //System.out.println(listroles.toString());
        model.addAttribute("buttonStatus", buttonStatus);
        model.addAttribute("user", userById);
        model.addAttribute("id", id);
        model.addAttribute("listroles",listroles);
        return "users/showUserRoles";
    }

}
