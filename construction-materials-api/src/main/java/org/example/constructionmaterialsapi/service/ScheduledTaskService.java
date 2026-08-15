package org.example.constructionmaterialsapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScheduledTaskService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupOldFiles() {
        log.info("Gundelik fayl temizleme prosesi basladi...");

        if (!Files.exists(fileStorageLocation)) {
            log.warn("Uploads qovlugu movcud deyil, temizleme dayandirildi");
            return;
        }

        try (Stream<Path> pathStream = Files.walk(fileStorageLocation, 1)) {
            pathStream.filter(Files::isRegularFile).forEach(path -> {
                try {
                    BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                    Instant fileCreationTime = attributes.creationTime().toInstant();

                    //24 saatdan sonra kohne fayl silmek ucun avtomatik
                    if (fileCreationTime.isBefore(Instant.now().minus(24, ChronoUnit.HOURS))) {
                        Files.delete(path);
                        log.info("Kohnelmis fayl ugurla silindi: {}", path.getFileName());
                    }
                } catch (IOException ex) {
                    log.error("Fayl silinerken xeta bas verdi: {}", path.getFileName(), ex);
                }
            });
            log.info("Gundelik fayl temizleme prosesi ugurla basa catdi.");
        } catch (IOException ex) {
            log.error("Qovlug oxunarken gozlenilmez xeta bas verdi " + ex);
        }

    }

}
