package qas.uicontroller.security.model;

import lombok.Data;

@Data
public class ValidatedToken {
    private boolean validated;
    private String message;
}
