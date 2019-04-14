package com.translate.bookmarks.flashcards.googletranslateflashcards.controller;

import com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse.UploadFileResponse;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.CsvParserService;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class CsvRestController {
    @Autowired
    private CsvParserService csvParserService;
    @Autowired
    private FileUploadService fileUploadService;


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        UploadFileResponse fileResponse = fileUploadService.uploadCsvFile(file);
        csvParserService.insertCsvFileToDB(fileResponse.getFile());
        return fileResponse;
    }
}
