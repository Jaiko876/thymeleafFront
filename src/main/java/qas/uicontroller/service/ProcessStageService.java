package qas.uicontroller.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qas.uicontroller.model.Role;
import qas.uicontroller.model.admin.ProcessStage;
import qas.uicontroller.model.view.ProcessStageViewModel;
import qas.uicontroller.service.IService.IProcessStage;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProcessStageService implements IProcessStage {
    private CookieService cookieService;

    public ProcessStageService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    public List<ProcessStage> getAllProcessStage(HttpServletRequest request) throws Exception {
        HttpEntity entityWithToken = cookieService.createEntityWithToken(request);
        ResponseEntity<ProcessStage[]> processStageArray = new RestTemplate().exchange(
                "http://localhost:8080/stage", HttpMethod.GET, entityWithToken , ProcessStage[].class);
        if (processStageArray.getStatusCode().isError()) {
            throw new Exception(processStageArray.getStatusCode().getReasonPhrase());
        }
        return Arrays.asList(processStageArray.getBody());
    }

    @Override
    public List<ProcessStageViewModel> convertToViewModelFromList(
            HttpServletRequest request, RoleService roleService,
            ProcessTypesService processTypesService, List<ProcessStage> processStageList) throws Exception {
        List<ProcessStageViewModel> processStageViewModels = new ArrayList<>();
        for (ProcessStage stage: processStageList) {
            ProcessStageViewModel processStageViewModel = new ProcessStageViewModel();
            processStageViewModel.setId(stage.getId_process_stage());
            processStageViewModel.setProcessTypeName(
                    processTypesService.getProcessTypeById(request, stage.getProcess_type_id()).getName());
            processStageViewModel.setStage(stage.getStage());
            processStageViewModel.setRoleName(roleService.getRoleByRoleId(request,stage.getRole_id())
                    .getName());
            processStageViewModel.setProcessTypeId(stage.getProcess_type_id());
            processStageViewModels.add(processStageViewModel);
        }
        return processStageViewModels;
    }

    @Override
    public List<Role> getAwailableRoles(HttpServletRequest request, int processTypeId, RoleService roleService,
                                        List<ProcessStage> stages, List<Role> roleList) throws Exception {
        List<ProcessStage> newStages = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        for (ProcessStage stage: stages) {
            if (stage.getProcess_type_id() == processTypeId)
                newStages.add(stage);
        }
        if (newStages.isEmpty())
            return new ArrayList<Role>();
        for (ProcessStage stage: newStages) {
            Role roleByRoleId = roleService.getRoleByRoleId(request, stage.getRole_id());
            roles.add(roleByRoleId);
        }
        return roleService.filterRoles(roles, roleList);
    }

    @Override
    public List<ProcessStage> getStagesForThisProcessType(HttpServletRequest request,
                                                          List<ProcessStage> processStageList,
                                                          int processTypeId) {
        List<ProcessStage> newStages = new ArrayList<>();
        for (ProcessStage stage: processStageList) {
            if (stage.getProcess_type_id() == processTypeId)
                newStages.add(stage);
        }
        return newStages;
    }
}
