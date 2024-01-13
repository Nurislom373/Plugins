package uz.devops.observability.exceptions.handler;

/**
 * @author Nurislom
 * @see uz.devops.observability.exceptions.handler
 * @since 12/19/2023 4:57 PM
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.devops.observability.exceptions.AlreadyCreatedException;
import uz.devops.observability.exceptions.BadRequestException;
import uz.devops.observability.exceptions.NotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyCreatedException.class})
    public ResponseEntity<String> alreadyCreatedExceptionHandler(AlreadyCreatedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<String> badRequestExceptionHandler(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
