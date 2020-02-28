package qas.uicontroller.model.view;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskViewModel {
    private int id;
    private String processName;
    private String userPerformerName;
    private String rolePerformerName;
    private Timestamp dateStart;
    private Timestamp dateEndPlanning;
    private Timestamp dateEndFact;
    private String statusName;
}
