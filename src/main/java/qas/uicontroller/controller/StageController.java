package qas.uicontroller.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.admin.ProcessStage;
import qas.uicontroller.model.view.ProcessStageViewModel;
import qas.uicontroller.service.ProcessStageService;
import qas.uicontroller.service.ProcessTypesService;
import qas.uicontroller.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class StageController {
    private ProcessStageService processStageService;
    private ProcessTypesService processTypesService;
    private RoleService roleService;

    public StageController(ProcessStageService processStageService, ProcessTypesService processTypesService, RoleService roleService) {
        this.processStageService = processStageService;
        this.processTypesService = processTypesService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "getAllProcessStages", method = RequestMethod.GET)
    public String getAllProcessStages(Model model, HttpServletRequest request) throws Exception {
        List<ProcessStage> allProcessStage = processStageService.getAllProcessStage(request);
        List<ProcessStageViewModel> body = processStageService.convertToViewModelFromList
                (request, roleService, processTypesService, allProcessStage);
        model.addAttribute("processStageList", body);
        return "admin/processStage/showProcessStageTable";
    }

    @RequestMapping(value = "getFilteredProcessStages", method = RequestMethod.GET)
    public String getFilteredProcessStages(Model model, HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String buttonStatus = "true";
        List<ProcessStage> allProcessStage = processStageService.getAllProcessStage(request);
        List<ProcessStage> stagesForThisProcessType = processStageService
                .getStagesForThisProcessType(request, allProcessStage, id);
        List<ProcessStageViewModel> body = processStageService.convertToViewModelFromList
                (request, roleService, processTypesService, stagesForThisProcessType);

        List<Role> listroles = roleService.getAllRoles(request);
        List<Role> awailableRoles = processStageService.getAwailableRoles
                (request, id, roleService, allProcessStage, listroles);
        if (stagesForThisProcessType.isEmpty()) {
            awailableRoles = listroles;
        }
        if (awailableRoles.isEmpty()) {
            buttonStatus = "";
        }

        ProcessType processTypeById = processTypesService.getProcessTypeById(request, id);
        String processTypeName = processTypeById.getName();

        model.addAttribute("buttonStatus", buttonStatus);
        model.addAttribute("processTypeName", processTypeName);
        model.addAttribute("id", id);
        model.addAttribute("processStageList", body);
        return "admin/processStage/showProcessStageTable";
    }
}
