package com.translate.bookmarks.flashcards.googletranslateflashcards.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class TranslateWord implements Serializable {


    @Id
    @GeneratedValue
    private Long id;
    private String fromLang;
    private String toLang;
    private String fromText;
    private String toText;
    private int hashCode;
    private int viewCount;

    public TranslateWord(String fromLang, String toLang, String fromText, String toText) {
        this.fromLang = fromLang;
        this.toLang = toLang;
        this.fromText = fromText;
        this.toText = toText;
        this.hashCode = hashCode();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFromLang(), getToLang(), getFromText(), getToText());
    }
}
