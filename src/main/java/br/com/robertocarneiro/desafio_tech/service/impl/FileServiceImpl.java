package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.service.ClientService;
import br.com.robertocarneiro.desafio_tech.service.FileService;
import br.com.robertocarneiro.desafio_tech.service.SaleService;
import br.com.robertocarneiro.desafio_tech.service.SalesmanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ClientService clientService;

    private final SalesmanService salesmanService;

    private final SaleService saleService;

    @Override
    public void processFile(File file) {
        try {
            List<String> lines = readAllLines(file);
            List<Salesman> salesmen = salesmanService.transformByLines(lines);

            int countClient = clientService.countClientByLines(lines);
            int countSalesman = salesmen.size();
            String moreExpensiveSaleId = saleService
                    .findMoreExpensiveSaleBySales(salesmen
                            .stream().map(Salesman::getSales).flatMap(Collection::stream).collect(Collectors.toList()))
                    .getId();
            String worstSalesmanName = salesmanService.findWorstSalesmanBySalesmen(salesmen).getName();
        } catch (Exception e) {
            log.error("Error to execute file: " + file.toPath().toString(), e);
        }
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
