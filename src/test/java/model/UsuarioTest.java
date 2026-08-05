package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UsuarioTest {

    @Test
    void deveConstruirUsuarioComTodosOsDados() {
        Departamento departamento = new Departamento("TI");
        departamento.setId(1);

        Usuario usuario = new Usuario(
                10,
                "Maria da Silva",
                "maria@example.com",
                "senha-hash",
                TipoUsuario.ADMIN,
                departamento
        );

        assertEquals(10, usuario.getId());
        assertEquals("Maria da Silva", usuario.getNome());
        assertEquals("maria@example.com", usuario.getEmail());
        assertEquals("senha-hash", usuario.getSenha());
        assertEquals(TipoUsuario.ADMIN, usuario.getTipo());
        assertEquals(departamento, usuario.getDepartamento());
        assertFalse(usuario.toString().contains("senha-hash"));
    }
}
