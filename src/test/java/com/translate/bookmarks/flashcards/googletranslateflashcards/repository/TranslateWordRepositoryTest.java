package com.translate.bookmarks.flashcards.googletranslateflashcards.repository;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TranslateWordRepositoryTest {

    public final TranslateWord mockTranslateWord = new TranslateWord("German", "Turkish", "wesen", "öz");

    @Autowired
    TranslateWordRepository translateWordRepository;


    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldSave() {
        translateWordRepository.save(mockTranslateWord);
        TranslateWord saved = translateWordRepository.findTrnslateWordByhashCode(mockTranslateWord.getHashCode());
        assertNotNull(saved);
    }

    @Test
    public void shouldFind() {
        translateWordRepository.save(mockTranslateWord);

        TranslateWord saved = translateWordRepository.findTrnslateWordByhashCode(mockTranslateWord.getHashCode());
        assertNotNull(saved);
        assertAll(
                () -> assertEquals(mockTranslateWord.getFromLang(), saved.getFromLang()),
                () -> assertEquals(mockTranslateWord.getToText(), saved.getToText()),
                () -> assertEquals(mockTranslateWord.getHashCode(), saved.getHashCode()),
                () -> assertEquals(mockTranslateWord.getFromText(), saved.getFromText())
        );

    }

    @Test
    public void findAllFromLanguage() {
        translateWordRepository.save(mockTranslateWord);
        translateWordRepository.save(new TranslateWord("English", "Turkish", "premature", "erken"));
        translateWordRepository.save(new TranslateWord("English", "Turkish", "clarity", "berraklık"));

        List<TranslateWord> results = translateWordRepository.findAllTrnslateWordByFromLangaue("English");
        List<TranslateWord> germanResults = translateWordRepository.findAllTrnslateWordByFromLangaue("German");
        assertEquals(2, results.size());
        assertEquals(1, germanResults.size());
        assertAll(
                () -> assertEquals(results.get(0).getFromLang(), "English"),
                () -> assertEquals(results.get(0).getToText(), "erken"),
                () -> assertEquals(results.get(0).getFromText(), "premature")
        );

    }

    @Test
    public void shouldDelete() {

        translateWordRepository.save(mockTranslateWord);
        TranslateWord saved = translateWordRepository.findById(1L).get();

        assertNotNull(saved);
        translateWordRepository.delete(mockTranslateWord);

        Optional<TranslateWord> savedT = translateWordRepository.findById(1L);
        assertFalse(savedT.isPresent());
    }

    @Test
    public void shouldFindAll() {

        translateWordRepository.save(mockTranslateWord);
        translateWordRepository.save(new TranslateWord("German", "Turkish", "asd", "öz"));


        List<TranslateWord> list = (List<TranslateWord>) translateWordRepository.findAll();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    public void shouldUpdate() {
        TranslateWord mtranslateWord = new TranslateWord("German", "asd", "wesen", "öz");

        translateWordRepository.save(mtranslateWord);

        translateWordRepository.incrementViewCountByOne(mtranslateWord.getHashCode());
        translateWordRepository.incrementViewCountByOne(mtranslateWord.getHashCode());

        TranslateWord updated = this.entityManager.persistFlushFind(translateWordRepository.findTrnslateWordByhashCode(mtranslateWord.getHashCode()));

        assertThat(2).isEqualTo(updated.getViewCount());
    }
}