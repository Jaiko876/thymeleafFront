package qas.uicontroller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.model.view.ProcessViewModel;
import qas.uicontroller.service.DataParser;
import qas.uicontroller.service.ProcessService;
import qas.uicontroller.service.ProcessTypesService;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProcessController {
    private DataParser dataParser;
    private ProcessService processService;
    private ProcessTypesService processTypesService;


    public ProcessController(DataParser dataParser, ProcessService processService, ProcessTypesService processTypesService1) {
        this.dataParser = dataParser;
        this.processService = processService;
        this.processTypesService = processTypesService1;
    }

    @RequestMapping(value = "processForm", method = RequestMethod.GET)
    public String addProcessForm(Model model, HttpServletRequest request) throws Exception {
        List<ProcessType> listprocesstypes = processTypesService.allProcessTypes(request);
        model.addAttribute("ptl",listprocesstypes);
        model.addAttribute("process", new Process());
        return "process/processForm";
    }

    @RequestMapping(value = "addProcess", method = RequestMethod.POST)
    public String addProcess(@ModelAttribute Process process, HttpServletRequest request) {
        try {
            Timestamp timestamp = dataParser.parseData(process.getTemp_date_end_planning());
            process.setDate_end_planning(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        processService.addProcess(process,request);

        return "redirect:/showMyProcesses";
    }
    @RequestMapping(value = "showMyProcesses", method = RequestMethod.GET)
    public String showMyProcesses(Model model, HttpServletRequest request) {
        try {
            List<ProcessViewModel> processes = processService.getUsersProcesses(request);
            model.addAttribute("processList", processes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "process/showMyProcesses";
    }
}
