package qas.uicontroller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.service.ProcessTypesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProcessTypeController {
    private ProcessTypesService processTypesService;

    public ProcessTypeController(ProcessTypesService processTypesService) {
        this.processTypesService = processTypesService;
    }

    @RequestMapping(value = "getAllProcessTypes", method = RequestMethod.GET)
    public String getAllProcessTypes(Model model, HttpServletRequest request) throws Exception {
        List<ProcessType> body = processTypesService.allProcessTypes(request);
        model.addAttribute("processTypeList", body);
        return "admin/processType/showProcessTypeTable";
    }
}
