package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.service.ClientService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ClientTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientTransformer clientTransformer;

    @Override
    public int countClientByLines(List<String> lines) {
        return clientTransformer.transformByLines(lines).size();
    }
}
