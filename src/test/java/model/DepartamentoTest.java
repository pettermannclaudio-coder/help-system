package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartamentoTest {

    @Test
    void deveArmazenarOsDadosDoDepartamento() {
        Departamento departamento = new Departamento("Tecnologia");
        departamento.setId(1);

        assertEquals(1, departamento.getId());
        assertEquals("Tecnologia", departamento.getNome());
        assertEquals("Tecnologia", departamento.toString());
    }
}
