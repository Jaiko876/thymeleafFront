package qas.uicontroller.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.IService.IUsersService;

@Component
public class UsersService implements IUsersService {
    @Override
    public UserWithoutPassword[] allUsers() {
        return new RestTemplate().getForObject("http://localhost:8080/user",UserWithoutPassword[].class);
    }
}
