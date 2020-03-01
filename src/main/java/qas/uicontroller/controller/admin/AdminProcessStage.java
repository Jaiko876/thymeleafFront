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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.admin.ProcessStage;
import qas.uicontroller.service.CookieService;
import qas.uicontroller.service.ProcessStageService;
import qas.uicontroller.service.ProcessTypesService;
import qas.uicontroller.service.RoleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProcessStage {
    private CookieService cookieService;
    private RoleService roleService;
    private ProcessTypesService processTypesService;
    private ProcessStageService processStageService;

    public AdminProcessStage(CookieService cookieService, RoleService roleService, ProcessTypesService processTypesService, ProcessStageService processStageService) {
        this.cookieService = cookieService;
        this.roleService = roleService;
        this.processTypesService = processTypesService;
        this.processStageService = processStageService;
    }

    @RequestMapping(value = "getProcessStageForm", method = RequestMethod.GET)
    public String getProcessStageForm(Model model, HttpServletRequest request) throws Exception {
        List<ProcessStage> allProcessStage = processStageService.getAllProcessStage(request);
        int id = Integer.parseInt(request.getParameter("id"));

        List<Role> listroles = roleService.getAllRoles(request);
        ProcessType processTypeById = processTypesService.getProcessTypeById(request, id);
        List<ProcessStage> processType = processStageService.getStagesForThisProcessType(request, allProcessStage, id);
        int actualStage = processStageService.getActualStage(request, processType);
            List<Role> awailableRoles = processStageService.getAwailableRoles
                    (request, id, roleService, allProcessStage, listroles);
            if (processType.isEmpty()) {
                awailableRoles = listroles;
            }
        List<Role> finalRoles = roleService.checkIfUsersHaveRoles(request, awailableRoles);
        model.addAttribute("listroles", finalRoles);
            model.addAttribute("processType", processTypeById);
            model.addAttribute("processStage", new ProcessStage());
            model.addAttribute("process_type_id", id);
            model.addAttribute("stage", actualStage);
            return "admin/processStage/processStageForm";
    }

    @RequestMapping(value = "addNewProcessStage", method = RequestMethod.POST)
    public ModelAndView addProcessStage(@ModelAttribute("processStage") ProcessStage processStage,
                                        HttpServletRequest request,
                                        @RequestParam("process_type_id") String id) throws Exception {
        Cookie cookie = cookieService.getCookie(request);
        HttpHeaders beaverHeader = cookieService.getBeaverHeader(cookie);
        HttpEntity<ProcessStage> processStageHttpEntity = new HttpEntity<>(processStage, beaverHeader);
        ResponseEntity<ProcessStage[]> exchange = new RestTemplate().exchange("http://localhost:8080/stage/type/{id}",
                HttpMethod.POST, processStageHttpEntity, ProcessStage[].class, processStage.getProcess_type_id());
        if (exchange.getStatusCode().isError()) {
            throw new Exception(exchange.getStatusCode().getReasonPhrase());
        }
        return new ModelAndView("redirect:../getFilteredProcessStages?id=" + id);
    }
}
