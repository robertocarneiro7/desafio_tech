package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.exception.SalesmanWithWorstResultNotFoundException;
import br.com.robertocarneiro.desafio_tech.service.SaleService;
import br.com.robertocarneiro.desafio_tech.service.SalesmanService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class SalesmanServiceImpl implements SalesmanService {

    private final SalesmanTransformer salesmanTransformer;

    private final SaleService saleService;

    @Override
    public List<Salesman> transformByLines(List<String> lines) {
        List<Salesman> salesmen = salesmanTransformer.transformByLines(lines);

        saleService.transformByLines(lines).stream()
                .collect(Collectors.groupingBy(Sale::getSalesmanName))
                .forEach((key, value) -> salesmen.stream()
                        .filter(salesman -> salesman.getName().equals(key))
                        .forEach(salesman -> salesman.setSales(value)));

        salesmen.forEach(salesman -> salesman.setTotal(salesman
                .getSales().stream().map(Sale::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add)));

        return salesmen;
    }

    @Override
    public Salesman findWorstSalesmanBySalesmen(List<Salesman> salesmen) {
        return salesmen.stream()
                .filter(salesman -> nonNull(salesman.getTotal()))
                .min(Comparator.comparing(Salesman::getTotal))
                .orElseThrow(() -> new SalesmanWithWorstResultNotFoundException("Salesman with worst result not found"));
    }
}
