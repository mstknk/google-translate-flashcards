package com.translate.bookmarks.flashcards.googletranslateflashcards.repository;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TranslateWordRepository extends CrudRepository<TranslateWord, Long> {

    @Modifying
    @Query(value = "UPDATE translate_word set view_count = view_count + 1 WHERE hash_code = :hashCode ", nativeQuery = true)
    @Transactional
    void incrementViewCountByOne(@Param("hashCode") int hashCode);

    @Query(value = "SELECT * FROM translate_word l WHERE l.hash_code = :hashCode ", nativeQuery = true)
    @Transactional
    @Cacheable("translateWord")
    TranslateWord findTrnslateWordByhashCode(@Param("hashCode") int hashCode);

    @Query(value = "SELECT * FROM translate_word l WHERE l.from_Lang = :fromLang ", nativeQuery = true)
    @Transactional
    List<TranslateWord> findAllTrnslateWordByFromLangaue(@Param("fromLang") String fromLang);
}
