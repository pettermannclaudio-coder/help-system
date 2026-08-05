package util;

import model.Usuario;

public final class SessaoUsuario {

    private static Usuario usuarioAtual;

    private SessaoUsuario() {
        throw new IllegalStateException("Classe utilitária não pode ser instanciada.");
    }

    public static void iniciar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário da sessão não pode ser nulo.");
        }

        usuarioAtual = usuario;
    }

    public static Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public static boolean estaAutenticado() {
        return usuarioAtual != null;
    }

    public static void encerrar() {
        usuarioAtual = null;
    }
}
