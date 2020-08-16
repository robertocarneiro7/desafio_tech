package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.service.SaleService;
import br.com.robertocarneiro.desafio_tech.service.SalesmanService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return salesmen;
    }
}
