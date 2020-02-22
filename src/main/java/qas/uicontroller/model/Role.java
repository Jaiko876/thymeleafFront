package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Role {
    @JsonProperty("id_role")
    private int idRole;
    @JsonProperty("name")
    private String name;
}
