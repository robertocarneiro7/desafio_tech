package br.com.robertocarneiro.desafio_tech.transformer.impl;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.service.ItemService;
import br.com.robertocarneiro.desafio_tech.transformer.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaleTransformer implements ObjectTransformer<Sale> {

    private static final String SALE_LINE_BAD_FORMATTED = "Sale line bad formatted";

    private final ItemService itemService;

    @Override
    public List<Sale> transformByLines(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith(Sale.IDENTIFY))
                .map(this::transformByLine)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Sale transformByLine(String line) {
        List<String> properties = Arrays.asList(line.split("ç"));
        if (properties.size() != Sale.PROPERTIES_NUMBER) {
            log.error(SALE_LINE_BAD_FORMATTED);
            throw new ObjectLineBadFormattedException(SALE_LINE_BAD_FORMATTED);
        }
        List<Item> items = itemService.transformByFullLine(properties.get(2));
        return Sale.builder()
                .id(properties.get(1))
                .items(items)
                .total(items.stream().map(Item::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add))
                .salesmanName(properties.get(3))
                .build();
    }

}
