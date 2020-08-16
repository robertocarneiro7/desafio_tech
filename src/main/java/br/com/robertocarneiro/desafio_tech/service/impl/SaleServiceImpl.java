package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.exception.SaleMoreExpensiveNotFoundException;
import br.com.robertocarneiro.desafio_tech.service.SaleService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SaleTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleTransformer saleTransformer;

    @Override
    public List<Sale> transformByLines(List<String> lines) {
        return saleTransformer.transformByLines(lines);
    }

    @Override
    public Sale findMoreExpensiveSaleBySales(List<Sale> sales) {
        return sales.stream()
                .filter(sale -> nonNull(sale.getTotal()))
                .max(Comparator.comparing(Sale::getTotal))
                .orElseThrow(() -> new SaleMoreExpensiveNotFoundException("Sale more expensive not found"));
    }
}
