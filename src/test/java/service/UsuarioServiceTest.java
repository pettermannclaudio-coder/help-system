package service;

import dao.UsuarioDAO;
import model.Departamento;
import model.TipoUsuario;
import model.Usuario;
import org.junit.jupiter.api.Test;
import util.PasswordUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioServiceTest {

    @Test
    void deveCadastrarUsuarioNormalizadoEComSenhaProtegida() {
        UsuarioDAOEmMemoria usuarioDAO = new UsuarioDAOEmMemoria();
        UsuarioService service = new UsuarioService(usuarioDAO);
        Departamento departamento = new Departamento("TI");
        departamento.setId(1);

        Usuario usuario = service.cadastrar(
                "  Maria   da Silva  ",
                "MARIA@EXAMPLE.COM",
                "Senha123",
                departamento
        );

        assertSame(usuario, usuarioDAO.usuarioSalvo);
        assertEquals("Maria da Silva", usuario.getNome());
        assertEquals("maria@example.com", usuario.getEmail());
        assertEquals(TipoUsuario.COMUM, usuario.getTipo());
        assertEquals(departamento, usuario.getDepartamento());
        assertNotEquals("Senha123", usuario.getSenha());
        assertTrue(PasswordUtil.verificarSenha("Senha123", usuario.getSenha()));
    }

    private static final class UsuarioDAOEmMemoria extends UsuarioDAO {

        private Usuario usuarioSalvo;

        @Override
        public Usuario buscarPorEmail(String email) {
            return null;
        }

        @Override
        public void salvar(Usuario usuario) {
            this.usuarioSalvo = usuario;
        }
    }
}
