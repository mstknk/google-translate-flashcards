package com.translate.bookmarks.flashcards.googletranslateflashcards.controller;


import com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse.UploadFileResponse;
import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.repository.TranslateWordRepository;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.CsvParserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CsvRestController {
    @Autowired
    private CsvParserService csvParserService;


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        UploadFileResponse fileResponse = csvParserService.uploadCsvFile(file);
        csvParserService.insertStringToDB(fileResponse.getContent());
        fileResponse.setContent("");
        return fileResponse;


    }
}
