package sit.int221.mytasksservice.dtos.response.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class AppErrorHandler extends Throwable {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ItemNotFoundException.class)
    public Map<String, Object> handleInvalidValueError(HttpServletRequest request){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", HttpStatus.NOT_FOUND);
        response.put("instance", request.getRequestURI());

        return response;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = GeneralException.class)
    public static Map<String, Object> handleInternalServerValueError(HttpServletRequest request ){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("instance", request.getRequestURI());
        return response;
    }


}
