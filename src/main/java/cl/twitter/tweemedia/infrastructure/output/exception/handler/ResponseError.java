package cl.twitter.tweemedia.infrastructure.output.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(value = Include.NON_NULL)
public class ResponseError {
    private String message;
    private Integer statusCode;
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    private List<ErrorDto> errors;

    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public ResponseError(String message, List<ErrorDto> errors) {
        this(message, null, errors);
    }

    public ResponseError(String message, Integer statusCode, List<ErrorDto> errors) {
        super();
        this.message = message;
        this.statusCode = statusCode;
        this.errors = errors;
    }
}