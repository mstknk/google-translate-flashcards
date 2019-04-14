package com.translate.bookmarks.flashcards.googletranslateflashcards.service;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.repository.TranslateWordRepository;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.util.CloseableIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class CsvParserService {

    @Autowired
    private TranslateWordRepository translateWordRepository;

    public void insertCsvFileToDB(File file) {
        Instant start = Instant.now();
        // Iterator
        int i = 0;
        try (CloseableIterator<String[]> it = CsvParser.iterator(file)) {
            while (it.hasNext()) {
                i++;
                String[] x = it.next();
                TranslateWord word = new TranslateWord(x[0], x[1], x[2], x[3]);
                if (translateWordRepository.findTrnslateWordByhashCode(word.getHashCode()) == null) {
                    translateWordRepository.save(word);
                    log.info(word + " insterted into database");
                }
                if (i % 100 == 0)
                    log.info(String.valueOf(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Instant end = Instant.now();
        log.info(file.getName() + " : " + Duration.between(start, end).toString());
    }


}
