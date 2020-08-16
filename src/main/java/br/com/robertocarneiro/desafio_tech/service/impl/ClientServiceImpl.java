package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.service.ClientService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ClientTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientTransformer clientTransformer;

    @Override
    public int countClientByLines(List<String> lines) {
        return clientTransformer.transformByLines(lines).size();
    }
}
