package br.com.robertocarneiro.desafio_tech.transformer.impl;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.exception.ItemLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.ObjectTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ItemTransformer implements ObjectTransformer<Item> {

    @Override
    public List<Item> transformByLines(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith(Item.START_IDENTIFY) && line.endsWith(Item.END_IDENTIFY))
                .map(line -> line.replaceAll("\\[", ""))
                .map(line -> line.replaceAll("\\]", ""))
                .map(line -> Arrays.asList(line.split(",")))
                .flatMap(Collection::stream)
                .map(this::transformByLine)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Item transformByLine(String line) {
        List<String> properties = Arrays.asList(line.split("-"));
        if (properties.size() != Item.PROPERTIES_NUMBER) {
            log.error("Item line bad formatted");
            throw new ItemLineBadFormattedException("Item line bad formatted");
        }
        try {
            BigDecimal quantity = new BigDecimal(properties.get(1));
            BigDecimal price = new BigDecimal(properties.get(2));
            return Item.builder()
                    .id(properties.get(0))
                    .quantity(quantity)
                    .price(price)
                    .build();
        }
        catch (Exception e) {
            log.error("Item number values bad formatted");
            throw e;
        }

    }

}
