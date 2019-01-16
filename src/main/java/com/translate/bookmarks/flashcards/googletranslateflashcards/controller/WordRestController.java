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

        return new ResponseEntity<List<TranslateWord>>(translateWordService.getAllWords(), HttpStatus.OK);

    }

    @GetMapping("/translateword/{hashcode}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TranslateWord> getWordByHashcode(@PathVariable String hashcode) {

        return new ResponseEntity<TranslateWord>(translateWordService.findTrnslateWordByhashCode(Integer.parseInt(hashcode)), HttpStatus.OK);

    }

    @PostMapping("/translateword/{hashcode}")
    @ResponseStatus(value = HttpStatus.OK)
    public void increaceview(@PathVariable String hashcode) {
        translateWordService.increaceView(Integer.parseInt(hashcode));
    }
}
