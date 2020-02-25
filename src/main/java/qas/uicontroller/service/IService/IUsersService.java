package qas.uicontroller.service.IService;

import qas.uicontroller.model.UserWithoutPassword;

import javax.servlet.http.HttpServletRequest;

public interface IUsersService {

    UserWithoutPassword[] allUsers(HttpServletRequest request);
}
