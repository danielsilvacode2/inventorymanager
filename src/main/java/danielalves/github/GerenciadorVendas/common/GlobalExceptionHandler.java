package danielalves.github.GerenciadorVendas.common;


import danielalves.github.GerenciadorVendas.exceptions.OperacaoNaoPermitidaException;
import danielalves.github.GerenciadorVendas.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {


        List<ErroCampo> collect = e.getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());


        ErroResponse erroResponse = new ErroResponse(HttpStatus.BAD_REQUEST.value(), "Campos invalidos: ", collect);


        return ResponseEntity.status(erroResponse.status()).body(erroResponse);

    }



    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> RegistroDuplicadoExceptionHandler(RegistroDuplicadoException e) {


        ErroResponse erroResponse = ErroResponse.conflito(e.getMessage());


        return ResponseEntity.status(erroResponse.status()).body(erroResponse);
    }


    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object>OperacaoNaoPermtidaExceptionHandler(OperacaoNaoPermitidaException e){


        ErroResponse erroResponse = new ErroResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),List.of());


        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResponse);

    }


    }
