package io.github.ferrazsergio.blog.api.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler para padronizar respostas de erro da API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> makeBody(HttpStatus status, String error, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return body;
    }

    // 404 - Recurso não encontrado (ex: EntityNotFoundException)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex,
                                                                    HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Map<String, Object> body = makeBody(status, "Not Found", ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    // 404 - rota não mapeada
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex,
                                                                    HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = String.format("Endpoint %s não existe", ex.getRequestURL());
        Map<String, Object> body = makeBody(status, "Not Found", message, request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    // 400 - validação de @Valid nos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                          HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // mapa campo -> mensagem
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));

        Map<String, Object> body = makeBody(status, "Bad Request", "Erro de validação dos campos", request.getRequestURI());
        body.put("errors", fieldErrors);
        return new ResponseEntity<>(body, status);
    }

    // 400 - JSON inválido / corpo mal formados
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                            HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> body = makeBody(status, "Bad Request", "Corpo da requisição inválido ou JSON mal formado", request.getRequestURI());
        body.put("detail", ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    // 409 - violação de integridade (ex: unique constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex,
                                                                   HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        Map<String, Object> body = makeBody(status, "Conflict", "Violação de integridade de dados", request.getRequestURI());
        body.put("detail", ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    // fallback - 500 para quaisquer exceções não tratadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = makeBody(status, "Internal Server Error", "Erro inesperado no servidor", request.getRequestURI());
        body.put("detail", ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
