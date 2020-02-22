package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Process implements Serializable {

    @JsonProperty("id_process")
    private Integer idProcess;
    @JsonProperty("process_type_id")
    private Integer processTypeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("user_start_id")
    private Integer userStartId;
    @JsonProperty("date_start")
    private Timestamp dateStart;
    @JsonProperty("date_end_planning")
    private Timestamp dateEndPlanning;
    @JsonProperty("date_end_fact")
    private Timestamp dateEndFact;
    @JsonProperty("status_id")
    private Integer statusId;

    public Integer getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(Integer idProcess) {
        this.idProcess = idProcess;
    }

    public Integer getProcessTypeId() {
        return processTypeId;
    }

    public void setProcessTypeId(Integer processTypeId) {
        this.processTypeId = processTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserStartId() {
        return userStartId;
    }

    public void setUserStartId(Integer userStartId) {
        this.userStartId = userStartId;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEndPlanning() {
        return dateEndPlanning;
    }

    public void setDateEndPlanning(Timestamp dateEndPlanning) {
        this.dateEndPlanning = dateEndPlanning;
    }

    public Timestamp getDateEndFact() {
        return dateEndFact;
    }

    public void setDateEndFact(Timestamp dateEndFact) {
        this.dateEndFact = dateEndFact;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Process process = (Process) o;
        return Objects.equals(this.idProcess, process.idProcess) &&
                Objects.equals(this.processTypeId, process.processTypeId) &&
                Objects.equals(this.name, process.name) &&
                Objects.equals(this.description, process.description) &&
                Objects.equals(this.userStartId, process.userStartId) &&
                Objects.equals(this.dateStart, process.dateStart) &&
                Objects.equals(this.dateEndPlanning, process.dateEndPlanning) &&
                Objects.equals(this.dateEndFact, process.dateEndFact) &&
                Objects.equals(this.statusId, process.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProcess, processTypeId, name, description, userStartId, dateStart, dateEndPlanning, dateEndFact, statusId);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Process {\n");

        sb.append("    idProcess: ").append(toIndentedString(idProcess)).append("\n");
        sb.append("    processTypeId: ").append(toIndentedString(processTypeId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    userStartId: ").append(toIndentedString(userStartId)).append("\n");
        sb.append("    dateStart: ").append(toIndentedString(dateStart)).append("\n");
        sb.append("    dateEndPlanning: ").append(toIndentedString(dateEndPlanning)).append("\n");
        sb.append("    dateEndFact: ").append(toIndentedString(dateEndFact)).append("\n");
        sb.append("    statusId: ").append(toIndentedString(statusId)).append("\n");
        sb.append("}");
        return sb.toString();
    }


    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
