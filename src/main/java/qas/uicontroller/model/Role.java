package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Role implements Serializable {
    @JsonProperty("id_role")
    private Integer idRole;

    @JsonProperty("name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!idRole.equals(role.idRole)) return false;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = idRole.hashCode();
        result = 31 * result + name.hashCode();
        return result;
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
