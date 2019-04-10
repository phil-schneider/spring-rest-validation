package com.philschneider.spring.restvalidation.rest;

import com.philschneider.spring.restvalidation.dto.InputDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
@Slf4j
public class DataRestController {

    @GetMapping
    public String getData() {
        return LocalDate.now().toString();
    }


    @PostMapping
    public String setNewData(@Valid @RequestBody InputDto input) {
        log.info("Received: " + input);
        return LocalTime.now().toString() + " - " + input;
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {

        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> new ValidationError(f.getObjectName(), f.getField(), "" + f.getRejectedValue(), f.getDefaultMessage()))
                //.map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        validationErrors.addAll(exception.getBindingResult().getGlobalErrors()
                .stream()
                .map(g -> new ValidationError(g.getObjectName(), null, null, g.getDefaultMessage()))
                .collect(Collectors.toList()));

        ErrorResponse error = new ErrorResponse(validationErrors);

        return ResponseEntity.badRequest().body(error);
    }

}
