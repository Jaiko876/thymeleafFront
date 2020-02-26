package qas.uicontroller.model.admin;

import lombok.Data;

@Data
public class ProcessStage {
    private int id_process_stage;
    private int process_type_id;
    private int stage;
    private int role_id;
}
