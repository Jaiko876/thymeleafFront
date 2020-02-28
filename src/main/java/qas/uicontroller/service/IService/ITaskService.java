package qas.uicontroller.service.IService;

import qas.uicontroller.model.Task;
import qas.uicontroller.model.view.TaskViewModel;
import qas.uicontroller.service.ProcessService;
import qas.uicontroller.service.RoleService;
import qas.uicontroller.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITaskService {
    List<Task> getAllTasks(HttpServletRequest request) throws Exception;
    List<TaskViewModel> getTaskViewModel(HttpServletRequest request,
                                         ProcessService processService, UsersService usersService,
                                         RoleService roleService, int processId) throws Exception;
    List<Task> filterByProcessId(HttpServletRequest request, int processId);
}
