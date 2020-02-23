package qas.uicontroller.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.security.JwtTokenProvider;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.DataParser;
import qas.uicontroller.service.ProcessService;
import qas.uicontroller.service.ProcessTypesService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProcessController {
    private CookieService cookieService;
    private DataParser dataParser;
    private ProcessService processService;


    public ProcessController(CookieService cookieService, DataParser dataParser, JwtTokenProvider jwtTokenProvider, ProcessTypesService processTypesService, ProcessService processService) {
        this.cookieService = cookieService;
        this.dataParser = dataParser;
        this.processService = processService;
    }

    @RequestMapping(value = "processForm", method = RequestMethod.GET)
    public String addProcessForm(Model model, HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<ProcessType[]> processTypeArray = new RestTemplate().exchange(
                "http://localhost:8080/type", HttpMethod.GET, entityWithToken , ProcessType[].class);
        if (processTypeArray.getBody() == null) {
            throw new Exception("Пустой RestTemplate");
        }
            List<ProcessType> listprocesstypes = Arrays.asList(processTypeArray.getBody());

        model.addAttribute("ptl",listprocesstypes);
        model.addAttribute("process", new Process());
        return "process/processForm";
    }
    /**
     * метод контроля добавления нового процесса, принимает параметры от формы ,
     * вызывает сервис добавления нового процесса
     * @param processType выбранный пользователем тип процесса
     * @param description заполненное пользователем описание процесса
     * @param date заполненная пользователем дата окончания процесса
     * @return временно перекидывает на домашнюю страницу
     */
    @RequestMapping(value = "addProcess", method = RequestMethod.POST)
    public String addProcess(@ModelAttribute Process process, HttpServletRequest request) {
        System.out.println(process);
        try {
            Timestamp timestamp = dataParser.parseData(process.getTemp_date_end_planning());
            process.setDate_end_planning(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        processService.addProcess(process,request);

        return "home";
    }
}
