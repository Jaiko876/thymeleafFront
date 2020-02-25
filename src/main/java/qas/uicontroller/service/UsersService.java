package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.IService.IUsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Component
public class UsersService implements IUsersService {
    private CookieService cookieService;

    public UsersService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public UserWithoutPassword[] allUsers(HttpServletRequest request) {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<UserWithoutPassword[]> usersArray = new RestTemplate().exchange("http://localhost:8080/user",
                HttpMethod.GET, entityWithToken, UserWithoutPassword[].class);
        return usersArray.getBody();
    }
}
