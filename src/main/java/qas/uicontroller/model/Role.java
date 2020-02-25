package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Role implements Serializable {
    @JsonProperty("id_role")
    private Integer idRole = null;

    @JsonProperty("name")
    private String name = null;

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(this.idRole, role.idRole) &&
                Objects.equals(this.name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Role {\n");

        sb.append("    idRole: ").append(toIndentedString(idRole)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
