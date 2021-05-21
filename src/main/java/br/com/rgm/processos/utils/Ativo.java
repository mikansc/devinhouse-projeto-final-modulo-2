package br.com.rgm.processos.utils;

public enum Ativo {
    SIM('S'),
    NAO('N');

    private char valor;

    Ativo(char valor) {
        this.valor = valor;
    }

    public char value() {
        return valor;
    }
}
