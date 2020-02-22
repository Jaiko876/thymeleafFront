package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class UserWithoutPassword implements Serializable {

    @JsonProperty("id_user")
    private Integer idUser;
    @JsonProperty("fio")
    private String fio;
    @JsonProperty("login")
    private String login;
    @JsonProperty("email")
    private String email;
    @JsonProperty("telegram_chat_id")
    private Integer telegramChatId;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Integer telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWithoutPassword userWithoutPassword = (UserWithoutPassword) o;
        return Objects.equals(this.idUser, userWithoutPassword.idUser) &&
                Objects.equals(this.fio, userWithoutPassword.fio) &&
                Objects.equals(this.login, userWithoutPassword.login) &&
                Objects.equals(this.email, userWithoutPassword.email) &&
                Objects.equals(this.telegramChatId, userWithoutPassword.telegramChatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, fio, login, email, telegramChatId);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserWithoutPassword {\n");

        sb.append("    idUser: ").append(toIndentedString(idUser)).append("\n");
        sb.append("    fio: ").append(toIndentedString(fio)).append("\n");
        sb.append("    login: ").append(toIndentedString(login)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    telegramChatId: ").append(toIndentedString(telegramChatId)).append("\n");
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
