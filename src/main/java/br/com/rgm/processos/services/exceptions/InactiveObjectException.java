package br.com.rgm.processos.services.exceptions;

public class InactiveObjectException extends RuntimeException {

    public InactiveObjectException(String message) {
        super(message);
    }
}
