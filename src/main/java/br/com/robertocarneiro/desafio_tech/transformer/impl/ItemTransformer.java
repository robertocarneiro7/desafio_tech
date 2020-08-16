package br.com.robertocarneiro.desafio_tech.transformer.impl;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ItemTransformer {

    private static final String ITEM_LINE_BAD_FORMATTED = "Item line bad formatted";

    public List<Item> transformByFullLine(String line) {
        if(!line.startsWith(Item.START_IDENTIFY) || !line.endsWith(Item.END_IDENTIFY)) {
            log.error(ITEM_LINE_BAD_FORMATTED);
            throw new ObjectLineBadFormattedException(ITEM_LINE_BAD_FORMATTED);
        }
        String cleanLine = line.replaceAll("\\[", "")
                .replaceAll("\\]", "");
        return Arrays.stream(cleanLine.split(","))
                .map(this::transformByLine)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Item transformByLine(String line) {
        List<String> properties = Arrays.asList(line.split("-"));
        if (properties.size() != Item.PROPERTIES_NUMBER) {
            log.error(ITEM_LINE_BAD_FORMATTED);
            throw new ObjectLineBadFormattedException(ITEM_LINE_BAD_FORMATTED);
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
