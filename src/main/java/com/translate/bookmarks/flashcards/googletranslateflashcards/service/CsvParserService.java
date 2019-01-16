package com.translate.bookmarks.flashcards.googletranslateflashcards.service;


import com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse.UploadFileResponse;
import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.repository.TranslateWordRepository;
import org.apache.commons.io.IOUtils;
import org.simpleflatmapper.csv.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CsvParserService {

    @Autowired
    private TranslateWordRepository translateWordRepository;

    public List<TranslateWord> getWordsByParsingFromCsvFile(File file) throws IOException {

        Function<String[], TranslateWord> arrayStringToTranslateWord = x -> new TranslateWord(x[0], x[1], x[2], x[3]);
        // Stream
        List<TranslateWord> words;
        try (Stream<String[]> stream = CsvParser.stream(file)) {
            words = stream.map(e -> arrayStringToTranslateWord.apply(e)).collect(Collectors.toList());
        }
        return words;
    }


    public List<TranslateWord> getWordsByParsingFromCsvFile(String theString) {

        Function<String[], TranslateWord> arrayStringToTranslateWord = x -> new TranslateWord(x[0], x[1], x[2], x[3]);
        // Stream
        List<TranslateWord> words = null;
        try (Stream<String[]> stream = CsvParser.stream(theString)) {
            words = stream.map(e -> arrayStringToTranslateWord.apply(e)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public void insertStringToDB(String content) {
        List<TranslateWord> words = getWordsByParsingFromCsvFile(content);
        List<TranslateWord> notExistingList = words.stream().filter(f -> {
            TranslateWord d = translateWordRepository.findTrnslateWordByhashCode(f.getHashCode());
            return d == null;
        }).collect(Collectors.toList());

        notExistingList.forEach(e -> translateWordRepository.save(e));
    }

    public UploadFileResponse uploadCsvFile(MultipartFile file) throws IOException {
        // Normalize file name
        // TODO doesnt like this change it later
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        StringWriter writer = new StringWriter();
        IOUtils.copy(file.getInputStream(), writer);
        String fileString = writer.toString();

        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Path.of(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(fileName + " uploaded file");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), fileString);
    }

}
