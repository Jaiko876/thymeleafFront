package qas.uicontroller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qas.uicontroller.model.view.TaskViewModel;
import qas.uicontroller.service.ProcessService;
import qas.uicontroller.service.RoleService;
import qas.uicontroller.service.TaskService;
import qas.uicontroller.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {
    private UsersService usersService;
    private TaskService taskService;
    private RoleService roleService;
    private ProcessService processService;

    public TaskController(UsersService usersService, TaskService taskService, RoleService roleService, ProcessService processService) {
        this.usersService = usersService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.processService = processService;
    }

    @RequestMapping(value = "tasks", method = RequestMethod.GET)
    public String showProcessTasks(Model model, HttpServletRequest request) throws Exception {
        List<TaskViewModel> taskViewModels = taskService.getTaskViewModel(request, processService, usersService, roleService,
                Integer.parseInt(request.getParameter("id")));
        model.addAttribute("tasks", taskViewModels);
        return "task/taskByProcess";
    }
}
