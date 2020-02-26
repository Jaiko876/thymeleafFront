package qas.uicontroller.service.IService;

import qas.uicontroller.model.Role;
import qas.uicontroller.model.UserWithoutPassword;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUsersService {

    UserWithoutPassword[] allUsers(HttpServletRequest request);
    UserWithoutPassword getUserById(HttpServletRequest request, int userId) throws Exception;
    List<Role> getUsersRoles(HttpServletRequest request, int userId) throws Exception;
}
