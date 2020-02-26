package qas.uicontroller.controller.admin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.service.CookieService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminRoleController {
    private CookieService cookieService;

    public AdminRoleController(CookieService cookieService) {
        this.cookieService = cookieService;
    }


    @RequestMapping(value = "addNewRole", method = RequestMethod.POST)
    public String createNewRole(@ModelAttribute("role") Role role, HttpServletRequest request) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<Role> roleHttpEntity = new HttpEntity<>(role, beaverHeader);
        ResponseEntity<Role[]> exchange = new RestTemplate().exchange("http://localhost:8080/role",
                HttpMethod.POST, roleHttpEntity, Role[].class);
        if (exchange.getStatusCode().isError()) {
            throw new Exception(exchange.getStatusCode().getReasonPhrase());
        }
        return "redirect:../roles";
    }

    @RequestMapping(value = "addRoleForm", method = RequestMethod.GET)
    public String showRoleForm(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("role", new Role());
        return "admin/role/addRole";
    }

}
