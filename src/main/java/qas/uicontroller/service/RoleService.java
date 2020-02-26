package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.service.IService.IRoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
