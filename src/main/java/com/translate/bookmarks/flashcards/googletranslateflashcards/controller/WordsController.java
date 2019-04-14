package com.translate.bookmarks.flashcards.googletranslateflashcards.controller;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.TranslateWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/")
@Slf4j
public class WordsController {


    @Autowired
    private TranslateWordService translateWordService;


    @RequestMapping({"/words", "/words/index", "/words/index.html", "/words.html"})
    public String listwords(Model model) {
        List<TranslateWord> words = translateWordService.getAllWordsByFromLang("English");
        log.info("total word site  : " + words.size());
        model.addAttribute("words", words);

        return "words/index";

    }

    @GetMapping("/api/words")
    public @ResponseBody
    List<TranslateWord> getwordsJson() {

        return translateWordService.getAllWords();
    }


}
