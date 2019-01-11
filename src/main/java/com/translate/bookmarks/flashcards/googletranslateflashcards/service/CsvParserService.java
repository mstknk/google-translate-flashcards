package com.translate.bookmarks.flashcards.googletranslateflashcards.service;


import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import org.simpleflatmapper.csv.CsvParser;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CsvParserService {


    public List<TranslateWord> getWordsByParsingFromCsvFile(File file) throws IOException {

        Function<String[], TranslateWord> arrayStringToTranslateWord = x -> new TranslateWord(x[0], x[1], x[2], x[3]);
        // Stream
        List<TranslateWord> words;
        try (Stream<String[]> stream = CsvParser.stream(file)) {
            words = stream.map(e -> arrayStringToTranslateWord.apply(e)).collect(Collectors.toList());
        }
        return words;
    }
}
