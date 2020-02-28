package qas.uicontroller.service.IService;

import qas.uicontroller.model.Role;
import qas.uicontroller.model.admin.ProcessStage;
import qas.uicontroller.model.view.ProcessStageViewModel;
import qas.uicontroller.service.ProcessTypesService;
import qas.uicontroller.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IProcessStage {
    List<ProcessStage> getAllProcessStage(HttpServletRequest request) throws Exception;
    List<Role> getAwailableRoles(HttpServletRequest request, int processTypeId, RoleService roleService,
                                    List<ProcessStage> stages, List<Role> roleList) throws Exception;
    List<ProcessStage> getStagesForThisProcessType(HttpServletRequest request, List<ProcessStage> processStageList,
                                                   int processTypeId);
    List<ProcessStageViewModel> convertToViewModelFromList(HttpServletRequest request, RoleService roleService,
                                                           ProcessTypesService processTypesService,
                                                           List<ProcessStage> processStageList) throws Exception;
    int getActualStage(HttpServletRequest request, List<ProcessStage> processStageList);
}
