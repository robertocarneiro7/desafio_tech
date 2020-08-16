package br.com.robertocarneiro.desafio_tech.exception;

public class SaleMoreExpensiveNotFoundException extends RuntimeException {

    public SaleMoreExpensiveNotFoundException(String message) {
        super(message);
    }
}
