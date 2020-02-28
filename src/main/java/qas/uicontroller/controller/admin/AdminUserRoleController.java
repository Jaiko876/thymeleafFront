package qas.uicontroller.controller.admin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.RoleService;
import qas.uicontroller.service.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserRoleController {
    private CookieService cookieService;
    private RoleService roleService;
    private UsersService usersService;

    public AdminUserRoleController(CookieService cookieService, RoleService roleService, UsersService usersService) {
        this.cookieService = cookieService;
        this.roleService = roleService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "showUserRoleForm", method = RequestMethod.GET)
    public String showUserRoleForm(Model model, HttpServletRequest request) throws Exception {
        List<Role> usersRoles = usersService.getUsersRoles(request, Integer.parseInt(request.getParameter("id")));
        List<Role> listroles = roleService.getAllRoles(request);
        List<Role> roleList = roleService.filterRoles(usersRoles, listroles);
        UserWithoutPassword userById = usersService.getUserById(request, Integer.parseInt(request.getParameter("id")));

        model.addAttribute("user", userById);
        model.addAttribute("role", new Role());
        model.addAttribute("listroles",roleList);
        model.addAttribute("user_id", request.getParameter("id"));
        return "admin/users/addRoles";
    }

    @RequestMapping(value = "addUserRole", method = RequestMethod.POST)
    public String addUserRole(@ModelAttribute("role") Role role,
                              @RequestParam("user_id") String userId,
                              HttpServletRequest request) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        Role roleByRoleId = roleService.getRoleByRoleId(request, role.getIdRole());
        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<Role> roleHttpEntity = new HttpEntity<>(roleByRoleId, beaverHeader);
        ResponseEntity<Void> exchange = new RestTemplate().exchange("http://localhost:8080/user/role/{id}",
                HttpMethod.POST, roleHttpEntity, Void.class, userId);
        if (exchange.getStatusCode().isError()) {
            throw new Exception(exchange.getStatusCode().getReasonPhrase());
        }
        return "redirect:../allusers";
    }
}
