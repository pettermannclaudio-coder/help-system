package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RespostaTest {

    @Test
    void deveArmazenarOsDadosDaResposta() {
        Usuario usuario = new Usuario();
        Solicitacao solicitacao = new Solicitacao();
        LocalDateTime dataResposta = LocalDateTime.of(2026, 8, 4, 15, 30);

        Resposta resposta = new Resposta(
                30,
                "Acesso liberado.",
                usuario,
                solicitacao,
                dataResposta
        );

        assertEquals(30, resposta.getId());
        assertEquals("Acesso liberado.", resposta.getDescricao());
        assertEquals(usuario, resposta.getUsuario());
        assertEquals(solicitacao, resposta.getSolicitacao());
        assertEquals(dataResposta, resposta.getDataResposta());
        assertEquals("Acesso liberado.", resposta.toString());
    }
}
