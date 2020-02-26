package qas.uicontroller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.Role;
import qas.uicontroller.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public String showRoles(Model model, HttpServletRequest request) throws Exception {
        List<Role> listroles= roleService.getAllRoles(request);
        model.addAttribute("listroles",listroles);
        return "admin/role/roles";
    }
}
