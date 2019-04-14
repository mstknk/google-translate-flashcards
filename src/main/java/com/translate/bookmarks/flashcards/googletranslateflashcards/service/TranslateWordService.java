package com.translate.bookmarks.flashcards.googletranslateflashcards.service;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.repository.TranslateWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TranslateWordService {

    @Autowired
    private TranslateWordRepository translateWordRepository;


    public List<TranslateWord> getAllWords() {

        return (List<TranslateWord>) translateWordRepository.findAll();
    }

    public List<TranslateWord> getAllWordsByFromLang(String fromLang) {

        return translateWordRepository.findAllTrnslateWordByFromLangaue(fromLang);
    }

    public TranslateWord findTrnslateWordByhashCode(int hashcode) {

        return translateWordRepository.findTrnslateWordByhashCode(hashcode);
    }

    @CacheEvict("translateWord")
    public TranslateWord increaceView(int hashcode) {

        translateWordRepository.incrementViewCountByOne(hashcode);
        return translateWordRepository.findTrnslateWordByhashCode(hashcode);
    }
}
