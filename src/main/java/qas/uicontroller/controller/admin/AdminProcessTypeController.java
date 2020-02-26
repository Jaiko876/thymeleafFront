package qas.uicontroller.controller.admin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.service.CookieService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminProcessTypeController {
    private CookieService cookieService;

    public AdminProcessTypeController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @RequestMapping(value = "getProcessTypeForm", method = RequestMethod.GET)
    public String getProcessTypeForm(Model model) {
        model.addAttribute("processType", new ProcessType());
        return "admin/processType/addProcessType";
    }

    @RequestMapping(value = "addNewProcessType", method = RequestMethod.POST)
    public String addProcessType(@ModelAttribute("processType") ProcessType processType, HttpServletRequest request) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<ProcessType> processTypeHttpEntity = new HttpEntity<>(processType, beaverHeader);
        ResponseEntity<ProcessType[]> exchange = new RestTemplate().exchange("http://localhost:8080/type",
                HttpMethod.POST, processTypeHttpEntity, ProcessType[].class);
        if (exchange.getStatusCode().isError()) {
            throw new Exception(exchange.getStatusCode().getReasonPhrase());
        }
        return "redirect:../getAllProcessTypes";
    }
}
