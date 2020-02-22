package qas.uicontroller.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.ProcessService;
import qas.uicontroller.service.ProcessTypesService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ProcessController {
    private CookieService cookieService;
    private static Map<String, Integer> typeProcessMap;
    private ProcessTypesService processTypesService;
    private ProcessService processService;


    public ProcessController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @RequestMapping(value = "processForm", method = RequestMethod.GET)
    public String addProcessForm(Model model, HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<ProcessType[]> processTypeArray = new RestTemplate().exchange(
                "http://localhost:8080/type", HttpMethod.GET, entityWithToken , ProcessType[].class);
        if (processTypeArray.getBody() != null) {
            List<ProcessType> listprocesstypes = Arrays.asList(processTypeArray.getBody());
            typeProcessMap = new HashMap<>();
            for (ProcessType pt : listprocesstypes) {
                typeProcessMap.put(pt.getName(), pt.getIdProcessType());
            }
            model.addAttribute("ptl",listprocesstypes);
        } else {
            throw new Exception("Пустой RestTemplate");
        }
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
    @RequestMapping(value = "addprocess", method = RequestMethod.GET)
    public String addProcess(@RequestParam("processtype") String processType,
                             @RequestParam("description") String description,
                             @RequestParam("date") String date, HttpServletRequest request) {

        Integer idType = 0;
        if (typeProcessMap != null) idType = typeProcessMap.get(processType);

        processService.addProcess(idType, description, date, request);

        return "index";
    }
}
