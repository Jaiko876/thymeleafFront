package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.*;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.view.TaskViewModel;
import qas.uicontroller.service.IService.ITaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService implements ITaskService {
    private CookieService cookieService;

    public TaskService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public List<Task> getAllTasks(HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Task[]> exchange = restTemplate.exchange("http://localhost:8080/task", HttpMethod.GET,
                entityWithToken, Task[].class);
        return Arrays.asList(Objects.requireNonNull(exchange.getBody()));
    }

    @Override
    public List<Task> filterByProcessId(HttpServletRequest request, int processId) {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Task[]> exchange = restTemplate.exchange("http://localhost:8080/process/{id}/task",
                HttpMethod.GET, entityWithToken, Task[].class, processId);
        return Arrays.asList(Objects.requireNonNull(exchange.getBody()));
    }

    @Override
    public List<TaskViewModel> getTaskViewModel(HttpServletRequest request, ProcessService processService,
                                                UsersService usersService, RoleService roleService,
                                                int processId) throws Exception {
        List<Task> tasks = filterByProcessId(request,processId);
        List<TaskViewModel> taskViewModels = new ArrayList<>();
        Process processById = processService.getProcessById(request, processId);
        String processName = processById.getName();

        for (Task task: tasks) {
            TaskViewModel taskViewModel = new TaskViewModel();
            taskViewModel.setId(task.getId_task());
            taskViewModel.setProcessName(processName);
            taskViewModel.setUserPerformerName(
                    usersService.getUserById(request, task.getUser_performer_id()).getFio());
            taskViewModel.setRolePerformerName(
                    roleService.getRoleByRoleId(request, task.getRole_performer_id()).getName());
            taskViewModel.setDateStart(task.getDate_start());
            taskViewModel.setDateEndPlanning(task.getDate_end_planning());
            taskViewModel.setDateEndFact(task.getDate_end_fact());
            taskViewModel.setStatusName(StatusType.valueOfLabel(task.getStatus_id() + "").name());
            taskViewModels.add(taskViewModel);
        }
        return taskViewModels;
    }
}
