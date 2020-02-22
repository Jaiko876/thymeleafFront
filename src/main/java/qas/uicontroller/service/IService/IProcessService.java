package qas.uicontroller.service.IService;

import qas.uicontroller.model.Process;

import javax.servlet.http.HttpServletRequest;

public interface IProcessService {
    boolean addProcess(Process process, HttpServletRequest request);
}
