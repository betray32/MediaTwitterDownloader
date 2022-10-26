package cl.twitter.tweemedia.infrastructure.output.exception.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonSerialize
@AllArgsConstructor
@Data
public class ErrorDto implements Serializable {
    private static final long serialVersionUID = 6058781533824057875L;
    private String message;
}