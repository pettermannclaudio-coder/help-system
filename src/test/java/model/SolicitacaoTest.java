package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolicitacaoTest {

    @Test
    void deveArmazenarOsDadosDaSolicitacao() {
        Usuario usuario = new Usuario();
        Departamento departamento = new Departamento();
        LocalDateTime dataCriacao = LocalDateTime.of(2026, 8, 4, 15, 0);

        Solicitacao solicitacao = new Solicitacao(
                20,
                "Acesso ao sistema",
                "Não consigo acessar o sistema interno.",
                usuario,
                "ABERTA",
                departamento,
                dataCriacao
        );

        assertEquals(20, solicitacao.getId());
        assertEquals("Acesso ao sistema", solicitacao.getTitulo());
        assertEquals("Não consigo acessar o sistema interno.", solicitacao.getDescricao());
        assertEquals(usuario, solicitacao.getUsuario());
        assertEquals("ABERTA", solicitacao.getStatus());
        assertEquals(dataCriacao, solicitacao.getDataCriacao());
        assertEquals("Acesso ao sistema", solicitacao.toString());
    }
}
