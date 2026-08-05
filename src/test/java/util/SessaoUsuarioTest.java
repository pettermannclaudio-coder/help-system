package util;

import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessaoUsuarioTest {

    @AfterEach
    void encerrarSessao() {
        SessaoUsuario.encerrar();
    }

    @Test
    void deveIniciarEEncerrarSessao() {
        Usuario usuario = new Usuario();

        SessaoUsuario.iniciar(usuario);

        assertTrue(SessaoUsuario.estaAutenticado());
        assertSame(usuario, SessaoUsuario.getUsuarioAtual());

        SessaoUsuario.encerrar();

        assertFalse(SessaoUsuario.estaAutenticado());
        assertNull(SessaoUsuario.getUsuarioAtual());
    }
}
