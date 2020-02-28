package qas.uicontroller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode
public class Task {
    private Integer id_task;
    private Integer process_id;
    private Integer user_performer_id;
    private Integer role_performer_id;
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp date_start;
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp date_end_planning;
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp date_end_fact;
    private Integer status_id;
}
