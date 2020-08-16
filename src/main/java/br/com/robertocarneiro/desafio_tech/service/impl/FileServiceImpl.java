package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Client;
import br.com.robertocarneiro.desafio_tech.service.FileService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ClientTransformer;
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

    private final ClientTransformer clientTransformer;

    @Override
    public void processFile(File file) {
        List<String> lines = readAllLines(file);
        List<Client> clients = clientTransformer.transformByLines(lines);
    }

    private List<String> readAllLines(File file) {
        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error to read all lines on file: " + file.toPath().toString());
            return new ArrayList<>();
        }
    }

}
