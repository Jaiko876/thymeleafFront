package qas.uicontroller.model;

public enum StatusType {
        ACTIVE("1"),
        SENDED("2"),
        WAIT("3"),
        AGREED("4"),
        DENIED("5"),
        CANCELED("6");
    public final String label;

    StatusType (String label) {
        this.label = label;
    }
    public static StatusType valueOfLabel(String label) {
        for (StatusType e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}