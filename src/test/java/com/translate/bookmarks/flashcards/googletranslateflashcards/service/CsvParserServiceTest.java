package com.translate.bookmarks.flashcards.googletranslateflashcards.service;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CsvParserService.class)
public class CsvParserServiceTest {

    @Autowired
    CsvParserService csvParserService;

    @Test
    public void csvParserTest() throws IOException {
        File file = new ClassPathResource(
                "csv-test.csv").getFile();

        List<TranslateWord> translateWords = csvParserService.getWordsByParsingFromCsvFile(file);
        TranslateWord epectedWord = translateWords.get(0);
        assertAll(
                () -> assertNotNull(translateWords),
                () -> assertEquals(22, translateWords.size())
        );

        assertAll(
                () -> assertNotNull(epectedWord),
                () -> assertNull(epectedWord.getId()),
                () -> assertEquals("English", epectedWord.getFromLang()),
                () -> assertEquals("Turkish", epectedWord.getToLang()),
                () -> assertEquals("impedes", epectedWord.getFromText()),
                () -> assertEquals("Engeller", epectedWord.getToText())
        );
        System.out.println(translateWords);
    }

    @Test
    public void csvParserTestByString() throws IOException {
        File file = new ClassPathResource(
                "csv-test.csv").getFile();
        InputStream targetStream = new FileInputStream(file);

        StringWriter writer = new StringWriter();
        IOUtils.copy(targetStream, writer);
        String fileString = writer.toString();

        List<TranslateWord> translateWords = csvParserService.getWordsByParsingFromCsvFile(fileString);
        TranslateWord epectedWord = translateWords.get(0);
        assertAll(
                () -> assertNotNull(translateWords),
                () -> assertEquals(22, translateWords.size())
        );

        assertAll(
                () -> assertNotNull(epectedWord),
                () -> assertNull(epectedWord.getId()),
                () -> assertEquals("English", epectedWord.getFromLang()),
                () -> assertEquals("Turkish", epectedWord.getToLang()),
                () -> assertEquals("impedes", epectedWord.getFromText()),
                () -> assertEquals("Engeller", epectedWord.getToText())
        );
        System.out.println(translateWords);
    }
}