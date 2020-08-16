package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.service.impl.ClientServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ClientTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientTransformer clientTransformer;

    @Test
    public void countClientByLinesSuccess() {
        when(clientTransformer.transformByLines(any())).thenReturn(new ArrayList<>());

        int countClient = clientService.countClientByLines(new ArrayList<>());

        assertEquals(0, countClient);
    }
}
