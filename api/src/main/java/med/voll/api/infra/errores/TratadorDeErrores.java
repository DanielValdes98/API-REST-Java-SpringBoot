package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class) // Si detecta esta excepcion, sale error 404
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build(); // Metodo si se detecta alguna excepcion de algun tipo
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Si detecta esta excepcion, sale error 400
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores); // Metodo si se detecta alguna excepcion de algun tipo
    }

    private record DatosErrorValidacion(String campo,String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
