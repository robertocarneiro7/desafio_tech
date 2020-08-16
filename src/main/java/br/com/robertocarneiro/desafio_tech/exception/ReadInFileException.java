package br.com.robertocarneiro.desafio_tech.exception;

public class ReadInFileException extends RuntimeException {

    public ReadInFileException(String message, Exception e) {
        super(message, e);
    }
}
