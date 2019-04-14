package com.translate.bookmarks.flashcards.googletranslateflashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoogleTranslateFlashcardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleTranslateFlashcardsApplication.class, args);
    }


}

