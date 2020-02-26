package qas.uicontroller.model.view;

import lombok.Data;

@Data
public class ProcessStageViewModel {
    private int id;
    private String processTypeName;
    private int stage;
    private String roleName;
    private int processTypeId;
}
