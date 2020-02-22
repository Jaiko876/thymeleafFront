package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Process;
import qas.uicontroller.security.JwtTokenProvider;
import qas.uicontroller.service.IService.IProcessService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Component
public class ProcessService implements IProcessService {
    private CookieService cookieService;
    private JwtTokenProvider jwtTokenProvider;

    public ProcessService(CookieService cookieService, JwtTokenProvider jwtTokenProvider) {
        this.cookieService = cookieService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean addProcess(Process process, HttpServletRequest request) {
        Cookie cookie = cookieService.getCookie(request);
        int userId = jwtTokenProvider.getId(cookie.getValue());

        process.setId_process(0); //Не важно
        process.setUser_start_id(userId);
        process.setDate_start(new Timestamp(System.currentTimeMillis()));
        process.setStatus_id(1); //Дефолтный статус - active

        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<Process> processHttpEntity = new HttpEntity<>(process, beaverHeader);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Process> processResponseEntity = restTemplate.exchange(
                "http://localhost:8080/process/process_type/{id}", HttpMethod.POST, processHttpEntity , Process.class,
                process.getProcess_type_id());

        /* вот это не проходит из-за нул полей*/
        //new RestTemplate().postForObject("http://localhost:8080/process/process_type/{id}",process,Process.class,process.getProcessTypeId());
        return true;
    }
}
