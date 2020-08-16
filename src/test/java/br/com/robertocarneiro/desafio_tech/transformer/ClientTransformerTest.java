package br.com.robertocarneiro.desafio_tech.transformer;

import br.com.robertocarneiro.desafio_tech.dto.Client;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ClientTransformer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTransformerTest {

    private ClientTransformer clientTransformer = new ClientTransformer();

    @Test
    public void whenAllLinesCorrectThenTransformByLinesWithClients() {
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural");

        List<Client> clients = clientTransformer.transformByLines(lines);

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals("2345675434544345", clients.get(0).getCnpj());
        assertEquals("Jose da Silva", clients.get(0).getName());
        assertEquals("Rural", clients.get(0).getBusinessArea());
        assertEquals("2345675433444345", clients.get(1).getCnpj());
        assertEquals("Eduardo Pereira", clients.get(1).getName());
        assertEquals("Rural", clients.get(1).getBusinessArea());
    }

    @Test
    public void whenContainsLineBadFormattedThenTransformByLinesThrowsObjectLineBadFormattedException() {
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRuralçTest",
                "001ç1234567891234çPedroç50000",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        assertThrows(ObjectLineBadFormattedException.class,
                () -> clientTransformer.transformByLines(lines));
    }
}
