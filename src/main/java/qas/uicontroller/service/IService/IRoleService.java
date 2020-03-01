package qas.uicontroller.service.IService;

import qas.uicontroller.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles(HttpServletRequest request) throws Exception;
    Role getRoleByRoleId(HttpServletRequest request, int id) throws Exception;
    List<Role> filterRoles(List<Role> userRoles, List<Role> roles);
    List<Role> checkIfUsersHaveRoles(HttpServletRequest request, List<Role> roles) throws Exception;
}
