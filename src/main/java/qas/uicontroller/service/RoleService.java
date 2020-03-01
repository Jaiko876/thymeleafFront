package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.UserWithoutPassword;
import qas.uicontroller.service.IService.IRoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService implements IRoleService {
    private CookieService cookieService;

    public RoleService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public List<Role> getAllRoles(HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<Role[]> roleArray = new RestTemplate().exchange("http://localhost:8080/role",
                HttpMethod.GET, entityWithToken, Role[].class);
        if (roleArray.getStatusCode().isError()) {
            throw new Exception(roleArray.getStatusCode().getReasonPhrase());
        }
        return Arrays.asList(roleArray.getBody());
    }

    @Override
    public Role getRoleByRoleId(HttpServletRequest request, int id) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<Role> roleArray = new RestTemplate().exchange("http://localhost:8080/role/{id}",
                HttpMethod.GET, entityWithToken, Role.class, id);
        if (roleArray.getStatusCode().isError()) {
            throw new Exception(roleArray.getStatusCode().getReasonPhrase());
        }
        return roleArray.getBody();
    }

    @Override
    public List<Role> filterRoles(List<Role> userRoles, List<Role> roles) {
        ArrayList<Role> roles1 = new ArrayList<>(userRoles);
        ArrayList<Role> roles2 = new ArrayList<>(roles);
        roles2.removeAll(roles1);
        return roles2;
    }

    @Override
    public List<Role> checkIfUsersHaveRoles(HttpServletRequest request, List<Role> roles) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        List<Role> newRoleList = new ArrayList<>();
        for (Role role: roles) {
            ResponseEntity<UserWithoutPassword[]> exchange = new RestTemplate().exchange("http://localhost:8080/role/{id}/user", HttpMethod.GET,
                    entityWithToken, UserWithoutPassword[].class, role.getIdRole());
            if (exchange.getStatusCode().isError()) {
                throw new Exception(exchange.getStatusCode().getReasonPhrase());
            }
            else if(Objects.requireNonNull(exchange.getBody()).length != 0) {
                newRoleList.add(role);
            }
        }
        return newRoleList;
    }
}
