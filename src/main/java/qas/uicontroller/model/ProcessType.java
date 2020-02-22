package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ProcessType implements Serializable {
    @JsonProperty("id_process_type")
    private Integer idProcessType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("time_to_do")
    private Integer timeToDo;

    public Integer getIdProcessType() {
        return idProcessType;
    }

    public void setIdProcessType(Integer idProcessType) {
        this.idProcessType = idProcessType;
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

    public Integer getTimeToDo() {
        return timeToDo;
    }

    public void setTimeToDo(Integer timeToDo) {
        this.timeToDo = timeToDo;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessType processType = (ProcessType) o;
        return Objects.equals(this.idProcessType, processType.idProcessType) &&
                Objects.equals(this.name, processType.name) &&
                Objects.equals(this.description, processType.description) &&
                Objects.equals(this.timeToDo, processType.timeToDo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProcessType, name, description, timeToDo);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProcessType {\n");

        sb.append("    idProcessType: ").append(toIndentedString(idProcessType)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    timeToDo: ").append(toIndentedString(timeToDo)).append("\n");
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
