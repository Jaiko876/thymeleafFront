package qas.uicontroller.model;

import lombok.Data;

@Data
public class RegForm {
    private String username;
    private String password;
    private String fio;
    private String email;
    private int telegramChatId;
}
