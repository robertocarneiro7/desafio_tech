package br.com.robertocarneiro.desafio_tech.listener;

import br.com.robertocarneiro.desafio_tech.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class FileListener implements FileChangeListener {

    private final FileService fileService;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        changeSet.stream()
                .map(ChangedFiles::getFiles)
                .flatMap(Collection::stream)
                .filter(changedFile -> nonNull(changedFile) && nonNull(changedFile.getFile()))
                .filter(changedFile -> Type.ADD.equals(changedFile.getType()) && !isLocked(changedFile.getFile().toPath()))
                .map(ChangedFile::getFile)
                .forEach(fileService::processFile);
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }
}
