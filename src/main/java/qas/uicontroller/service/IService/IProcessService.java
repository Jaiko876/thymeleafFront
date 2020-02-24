package qas.uicontroller.service.IService;

import org.springframework.ui.Model;
import qas.uicontroller.model.Process;
import qas.uicontroller.model.ProcessType;
import qas.uicontroller.model.view.ProcessViewModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IProcessService {
    boolean addProcess(Process process, HttpServletRequest request);
    List<ProcessViewModel> getUsersProcesses(HttpServletRequest request) throws Exception;
}
