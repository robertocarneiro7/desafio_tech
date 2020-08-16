package br.com.robertocarneiro.desafio_tech.config;

import br.com.robertocarneiro.desafio_tech.listener.FileListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FileWatcherConfig {

    private static final String PATH = System.getProperty("user.home") + File.separator + "data" + File.separator + "in";

    private final FileListener fileListener;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(500L), Duration.ofMillis(300L));
        fileSystemWatcher.addSourceDirectory(new File(PATH));
        fileSystemWatcher.addListener(fileListener);
        fileSystemWatcher.start();
        log.info("Started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}
