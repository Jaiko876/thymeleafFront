package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Process;
import qas.uicontroller.service.IService.IProcessService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Component
public class ProcessService implements IProcessService {
    private CookieService cookieService;

    public ProcessService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public boolean addProcess(int idtype, String description, String date, HttpServletRequest request) {

        Process process = new Process();
        process.setProcessTypeId(idtype);
        process.setDescription(description);
        System.out.println(date); // эхо временно
        process.setDateEndPlanning(Timestamp.valueOf(date.substring(0,9)+" "+date.substring(11,15)+":00.0"));
        process.setUserStartId(4); // временно заполняем пользователя с id=4
        System.out.println(process.toString()); // эхо временно

        Cookie cookie = cookieService.getCookie(request);
        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<Process> processHttpEntity = new HttpEntity<>(process, beaverHeader);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Process> processResponseEntity = restTemplate.exchange(
                "http://localhost:8080/process/process_type/{id}", HttpMethod.POST, processHttpEntity , Process.class,
                process.getProcessTypeId());

        /* вот это не проходит из-за нул полей*/
        //new RestTemplate().postForObject("http://localhost:8080/process/process_type/{id}",process,Process.class,process.getProcessTypeId());
        return true;
    }
}
