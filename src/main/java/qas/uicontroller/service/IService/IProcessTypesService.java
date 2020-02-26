package qas.uicontroller.service.IService;


import qas.uicontroller.model.ProcessType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IProcessTypesService {

    List<ProcessType> allProcessTypes(HttpServletRequest request) throws Exception;
    ProcessType getProcessTypeById(HttpServletRequest request, int id) throws Exception;
}
