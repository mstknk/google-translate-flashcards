package com.translate.bookmarks.flashcards.googletranslateflashcards.controller;


import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.TranslateWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordRestController {
    @Autowired
    private TranslateWordService translateWordService;

    @GetMapping("allwords")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TranslateWord>> getAllWords() {

        return new ResponseEntity<>(translateWordService.getAllWords(), HttpStatus.OK);

    }

    @GetMapping("/translateword/{hashcode}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TranslateWord> getWordByHashcode(@PathVariable String hashcode) {

        return new ResponseEntity<>(translateWordService.findTrnslateWordByhashCode(Integer.parseInt(hashcode)), HttpStatus.OK);

    }

    @GetMapping("allwordsby/{fromLang}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TranslateWord>> getAllWordByFromLang(@PathVariable String fromLang) {
        return new ResponseEntity<>(translateWordService.getAllWordsByFromLang(fromLang), HttpStatus.OK);

    }

    @PostMapping("/translateword/{hashcode}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TranslateWord> increaceview(@PathVariable String hashcode) {
        return new ResponseEntity<>(translateWordService.increaceView(Integer.parseInt(hashcode)), HttpStatus.OK);
    }
}
