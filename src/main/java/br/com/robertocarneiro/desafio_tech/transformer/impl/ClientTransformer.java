package br.com.robertocarneiro.desafio_tech.transformer.impl;

import br.com.robertocarneiro.desafio_tech.dto.Client;
import br.com.robertocarneiro.desafio_tech.transformer.ObjectTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ClientTransformer implements ObjectTransformer<Client> {

    @Override
    public List<Client> transformByLines(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith(Client.IDENTIFY))
                .map(this::transformByLine)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Client transformByLine(String line) {
        List<String> properties = Arrays.asList(line.split("ç"));
        if (properties.size() != Client.PROPERTIES_NUMBER) {
            log.error("Line bad formatted");
            return null;
        }
        return Client.builder()
                .cnpj(properties.get(1))
                .name(properties.get(2))
                .businessArea(properties.get(3))
                .build();
    }

}
