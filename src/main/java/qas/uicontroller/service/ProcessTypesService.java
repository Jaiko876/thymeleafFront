package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.service.IService.IProcessTypesService;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Service
public class ProcessTypesService implements IProcessTypesService {
    private CookieService cookieService;

    public ProcessTypesService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public List<ProcessType> allProcessTypes(HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<ProcessType[]> processTypeArray = new RestTemplate().exchange(
                "http://localhost:8080/type", HttpMethod.GET, entityWithToken , ProcessType[].class);
        if (processTypeArray.getStatusCode().isError()) {
            throw new Exception(processTypeArray.getStatusCode().getReasonPhrase());
        }
        return Arrays.asList(processTypeArray.getBody());
    }

    @Override
    public ProcessType getProcessTypeById(HttpServletRequest request, int id) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<ProcessType> processTypeArray = new RestTemplate().exchange(
                "http://localhost:8080/type/{id}", HttpMethod.GET, entityWithToken , ProcessType.class, id);
        if (processTypeArray.getStatusCode().isError()) {
            throw new Exception(processTypeArray.getStatusCode().getReasonPhrase());
        }
        return processTypeArray.getBody();
    }
}
