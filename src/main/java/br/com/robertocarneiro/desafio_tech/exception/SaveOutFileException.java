package br.com.robertocarneiro.desafio_tech.exception;

public class SaveOutFileException extends RuntimeException {

    public SaveOutFileException(String message, Exception e) {
        super(message, e);
    }
}
