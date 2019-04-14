package com.translate.bookmarks.flashcards.googletranslateflashcards.controller;

import com.translate.bookmarks.flashcards.googletranslateflashcards.model.TranslateWord;
import com.translate.bookmarks.flashcards.googletranslateflashcards.service.TranslateWordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(WordRestController.class)
class WordRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateWordService translateWordService;


    @Test
    void getAllWords() throws Exception {
        when(translateWordService.getAllWords())
                .thenReturn(Arrays.asList(new TranslateWord("German", "Turkish", "wesen", "öz")));
        ResultActions resultActions = mockMvc.perform(get("/allwords")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fromLang", is("German")))
                .andExpect(jsonPath("$[0].fromText", is("wesen")))
                .andExpect(jsonPath("$[0].toText", is("öz")));
    }

    @Test
    void getWord() throws Exception {
        when(translateWordService.findTrnslateWordByhashCode(anyInt()))
                .thenReturn(new TranslateWord("German", "Turkish", "wesen", "öz"));
        ResultActions resultActions = mockMvc.perform(get("/translateword/123")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    void getWordByFromLang() throws Exception {
        when(translateWordService.getAllWordsByFromLang("German"))
                .thenReturn(Arrays.asList(
                        new TranslateWord("German", "Turkish", "wesen", "öz")));
        ResultActions resultActions = mockMvc.perform(get("/allwordsby/German")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fromLang", is("German")))
                .andExpect(jsonPath("$[0].fromText", is("wesen")))
                .andExpect(jsonPath("$[0].toText", is("öz")));
    }

    @Test
    void increaceview() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/translateword/123")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
}