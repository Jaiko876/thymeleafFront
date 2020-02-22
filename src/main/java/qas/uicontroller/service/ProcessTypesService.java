package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.service.IService.IProcessTypesService;

import javax.servlet.http.Cookie;


@Component
public class ProcessTypesService implements IProcessTypesService {
    private CookieService cookieService;

    public ProcessTypesService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public ProcessType[] allProcessTypes() {

        return new RestTemplate().getForObject("http://localhost:8080/type",ProcessType[].class);
    }

}
