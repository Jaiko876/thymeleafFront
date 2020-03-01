package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.StatusType;
import qas.uicontroller.model.view.ProcessViewModel;
import qas.uicontroller.security.JwtTokenProvider;
import qas.uicontroller.service.IService.IProcessService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProcessService implements IProcessService {
    private CookieService cookieService;
    private JwtTokenProvider jwtTokenProvider;
    private ProcessTypesService processTypesService;

    public ProcessService(CookieService cookieService, JwtTokenProvider jwtTokenProvider, ProcessTypesService processTypesService) {
        this.cookieService = cookieService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.processTypesService = processTypesService;
    }

    @Override
    public void addProcess(Process process, HttpServletRequest request) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        int userId = jwtTokenProvider.getId(cookie.getValue());

        process.setUser_start_id(userId);

        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<Process> processHttpEntity = new HttpEntity<>(process, beaverHeader);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Process> pResponseEntity=restTemplate.exchange("http://localhost:8080/process/process_type/{id}", HttpMethod.POST, processHttpEntity , Process.class,
                process.getProcess_type_id());
        if (pResponseEntity.getStatusCode().isError()) {
            throw new Exception(pResponseEntity.getStatusCode().toString());
        }
    }

    public List<ProcessViewModel> getUsersProcesses(HttpServletRequest request) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        String token = cookieService.getToken(cookie);
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        int userId = jwtTokenProvider.getId(token);
        ResponseEntity<Process[]> processArray = new RestTemplate().exchange(
                "http://localhost:8080/user/process/{id}", HttpMethod.GET,
                entityWithToken , Process[].class, userId);
        if (processArray.getStatusCode().isError()) {
            throw new Exception(processArray.getStatusCode().getReasonPhrase());
        }

        List<Process> processList = Arrays.asList(processArray.getBody());
        List<ProcessViewModel> processViewModels = new ArrayList<>();
        for (Process process: processList) {
            ProcessViewModel pvm = new ProcessViewModel(
                    process.getId_process(), processTypesService.getProcessTypeById(
                            request, process.getProcess_type_id()).getName() ,
                    process.getName(),process.getDescription(),process.getDate_start(),
                    process.getDate_end_planning(),process.getDate_end_fact(),
                    StatusType.valueOfLabel(process.getStatus_id().toString()).name()
            );
            processViewModels.add(pvm);
        }

        return processViewModels;
    }

    @Override
    public Process getProcessById(HttpServletRequest request, int id) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Process> exchange = restTemplate.exchange("http://localhost:8080/process/{id}", HttpMethod.GET, entityWithToken,
                Process.class, id);
        return exchange.getBody();
    }
}
