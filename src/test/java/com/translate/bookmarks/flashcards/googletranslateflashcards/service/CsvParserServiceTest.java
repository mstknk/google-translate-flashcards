package com.translate.bookmarks.flashcards.googletranslateflashcards.service;

import com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse.UploadFileResponse;
import com.translate.bookmarks.flashcards.googletranslateflashcards.exception.UnsupportedFileTypeException;
import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.repository.TranslateWordRepository;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("csv file service tests")
public class CsvParserServiceTest {

    @Autowired
    CsvParserService csvParserService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    TranslateWordRepository translateWordRepository;

    private static MultipartFile multipartFile;

    @BeforeAll
    static void setUp() throws IOException {
        File file = new ClassPathResource(
                "csv-test.csv").getFile();
        multipartFile = getMultipartFile(file);

    }

    @Test
    public void fileUploadTest() throws IOException {

        UploadFileResponse fileResponse = fileUploadService.uploadCsvFile(multipartFile);
        assertAll(
                () -> assertNotNull(fileResponse),
                () -> assertEquals(700, fileResponse.getSize()),
                () -> assertEquals("csv-test.csv", fileResponse.getFileName())
        );
    }

    @Test
    public void shouldThrowUnsupportedFileTypeException() throws IOException {

        File file = new ClassPathResource(
                "application.yml").getFile();
        MultipartFile multipartNotSupportFile = getMultipartFile(file);
        assertThrows(UnsupportedFileTypeException.class,
                () -> fileUploadService.uploadCsvFile(multipartNotSupportFile)
        );
    }

    @Test
    public void csvParserTest() throws IOException {

        UploadFileResponse fileResponse = fileUploadService.uploadCsvFile(multipartFile);
        csvParserService.insertCsvFileToDB(fileResponse.getFile());
        List<TranslateWord> translateWords = new ArrayList<>();
        translateWordRepository.findAll().iterator().forEachRemaining(translateWords::add);
        TranslateWord epectedWord = translateWords.get(0);
        assertAll(
                () -> assertNotNull(translateWords),
                () -> assertEquals(22, translateWords.size())
        );

        assertAll(
                () -> assertNotNull(epectedWord),
                () -> assertEquals(1, epectedWord.getId().intValue()),
                () -> assertEquals("English", epectedWord.getFromLang()),
                () -> assertEquals("Turkish", epectedWord.getToLang()),
                () -> assertEquals("impedes", epectedWord.getFromText()),
                () -> assertEquals("Engeller", epectedWord.getToText())
        );
        System.out.println(translateWords);
    }


    private static MultipartFile getMultipartFile(File file) {
        MultipartFile multipartFile = null;
        try {
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file",
                    file.getName(), new Tika().detect(file), IOUtils.toByteArray(input));
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile;
    }
}