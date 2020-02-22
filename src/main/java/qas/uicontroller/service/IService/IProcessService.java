package qas.uicontroller.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface IProcessService {
    boolean addProcess(int idtype, String description, String date, HttpServletRequest request);
}
