package model;

public enum PrioridadeSolicitacao {
    BAIXA(1),
    MEDIA(2),
    ALTA(3);

    private final int peso;

    PrioridadeSolicitacao(int peso) {
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }
}
