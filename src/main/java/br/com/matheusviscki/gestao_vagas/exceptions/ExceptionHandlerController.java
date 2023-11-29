package br.com.matheusviscki.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlerController {
    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ErrorMessageDTO> lstDTO = new ArrayList<>();
        var lstErrors = exception.getBindingResult().getFieldErrors();

        lstErrors.forEach(error -> {
            var message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            var errorMessageDTO = new ErrorMessageDTO(message, error.getField());

            lstDTO.add(errorMessageDTO);
        });

        return new ResponseEntity<>(lstDTO, HttpStatus.BAD_REQUEST);
    }
}
