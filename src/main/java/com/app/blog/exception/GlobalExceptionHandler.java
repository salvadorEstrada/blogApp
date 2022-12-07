package com.app.blog.exception;

import com.app.blog.dto.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice//maneja todas las capturas de excepciones de nuestra aplicacion
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    //ErrorDetalles es un DTO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
          ErrorDetalles errorDetalles = new ErrorDetalles(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
          return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //cuando los datos que le estoy enviando no son validos, me va a mandar una exception
    //De acuerdo a los mensajes que se han puesto en el DTO, (PublicacionDTO)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       Map<String, String > errores = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error)->{
           String nombreCampo=((FieldError)error).getField();//obtiene el campo del error
           String mensaje = error.getDefaultMessage();//el mensaje de error
           errores.put(nombreCampo, mensaje);
       });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
