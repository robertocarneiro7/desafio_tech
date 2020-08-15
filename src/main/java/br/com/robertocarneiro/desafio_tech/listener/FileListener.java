package br.com.robertocarneiro.desafio_tech.listener;

import br.com.robertocarneiro.desafio_tech.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileListener implements FileChangeListener {

    private final FileService fileService;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        changeSet.stream()
                .map(ChangedFiles::getFiles)
                .flatMap(Collection::stream)
                .filter(changedFile -> nonNull(changedFile) && nonNull(changedFile.getFile())
                        && nonNull(changedFile.getFile().toPath()))
                .filter(changedFile -> Type.ADD.equals(changedFile.getType()) && !isLocked(changedFile.getFile().toPath()))
                .map(this::readAllLines)
                .forEach(fileService::processFileByLines);
    }

    private List<String> readAllLines(ChangedFile changedFile) {
        try {
            return Files.readAllLines(changedFile.getFile().toPath());
        } catch (IOException e) {
            log.error("Error to read all lines on file: " + changedFile.getFile().toPath().toString());
            return new ArrayList<>();
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }
}
