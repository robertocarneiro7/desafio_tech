package br.com.robertocarneiro.desafio_tech.transformer.impl;

import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.ObjectTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SalesmanTransformer implements ObjectTransformer<Salesman> {

    private static final String SALESMAN_LINE_BAD_FORMATTED = "Salesman line bad formatted";

    @Override
    public List<Salesman> transformByLines(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith(Salesman.IDENTIFY))
                .map(this::transformByLine)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Salesman transformByLine(String line) {
        List<String> properties = Arrays.asList(line.split("ç"));
        if (properties.size() != Salesman.PROPERTIES_NUMBER) {
            log.error(SALESMAN_LINE_BAD_FORMATTED);
            throw new ObjectLineBadFormattedException(SALESMAN_LINE_BAD_FORMATTED);
        }
        return Salesman.builder()
                .cpf(properties.get(1))
                .name(properties.get(2))
                .salary(properties.get(3))
                .build();
    }

}
