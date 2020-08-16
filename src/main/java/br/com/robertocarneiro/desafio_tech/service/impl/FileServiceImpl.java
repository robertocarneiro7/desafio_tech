package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.service.ClientService;
import br.com.robertocarneiro.desafio_tech.service.FileService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SaleTransformer;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ClientService clientService;

    private final SaleTransformer saleTransformer;

    private final SalesmanTransformer salesmanTransformer;

    @Override
    public void processFile(File file) {
        try {
            List<String> lines = readAllLines(file);
            List<Sale> sales = saleTransformer.transformByLines(lines);
            List<Salesman> salesmen = salesmanTransformer.transformByLines(lines);

            fillSalesOnSalesmen(sales, salesmen);

            int countClient = clientService.countClientByLines(lines);
        } catch (Exception e) {
            log.error("Error to execute file: " + file.toPath().toString(), e);
        }
    }

    private void fillSalesOnSalesmen(List<Sale> sales, List<Salesman> salesmen) {
        sales.stream()
                .collect(Collectors.groupingBy(Sale::getSalesmanName))
                .forEach((key, value) -> salesmen.stream()
                        .filter(salesman -> salesman.getName().equals(key))
                        .forEach(salesman -> salesman.setSales(value)));
    }

    private List<String> readAllLines(File file) {
        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error to read all lines on file: " + file.toPath().toString(), e);
            return new ArrayList<>();
        }
    }

}
