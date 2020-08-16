package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.exception.SaveOutFileException;
import br.com.robertocarneiro.desafio_tech.service.ClientService;
import br.com.robertocarneiro.desafio_tech.service.FileService;
import br.com.robertocarneiro.desafio_tech.service.SaleService;
import br.com.robertocarneiro.desafio_tech.service.SalesmanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String PATH_OUT = System.getProperty("user.home") + File.separator + "data" + File.separator + "out";
    private static final String ERROR_TO_SAVE_OUT_FILE = "Error to save out file";
    private static final String ERROR_TO_READ_ALL_LINES_ON_FILE = "Error to read all lines on file: ";

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

            String textToSave = countClient + "\n" + countSalesman + "\n" + moreExpensiveSaleId + "\n" + worstSalesmanName;
            writeOutFile(textToSave, file.getName());

            log.info("Finished execution of the file: " + file.toPath().toString());
        } catch (Exception e) {
            if (isNull(file)) {
                log.error("File is null", e);
            }
            else {
                log.error("Error to execute file: " + file.toPath().toString(), e);
            }
        }
    }

    private List<String> readAllLines(File file) {
        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(ERROR_TO_READ_ALL_LINES_ON_FILE + file.toPath().toString(), e);
            throw new SaveOutFileException(ERROR_TO_READ_ALL_LINES_ON_FILE + file.toPath().toString(), e);
        }
    }

    private void writeOutFile(String textToSave, String fileName) {
        File file = new File(PATH_OUT + File.separator + fileName);
        file.getParentFile().mkdirs();
        try(FileWriter fw = new FileWriter(file.getAbsoluteFile())) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textToSave);
            bw.close();
        }
        catch (IOException e){
            log.error(ERROR_TO_SAVE_OUT_FILE);
            throw new SaveOutFileException(ERROR_TO_SAVE_OUT_FILE, e);
        }
    }

}
