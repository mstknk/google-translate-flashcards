package com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.File;

@Data
@AllArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private File file;

}