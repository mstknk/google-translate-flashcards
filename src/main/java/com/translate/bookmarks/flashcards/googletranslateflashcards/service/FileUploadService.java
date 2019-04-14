package com.translate.bookmarks.flashcards.googletranslateflashcards.service;

import com.translate.bookmarks.flashcards.googletranslateflashcards.apiresponse.UploadFileResponse;
import com.translate.bookmarks.flashcards.googletranslateflashcards.exception.UnsupportedFileTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class FileUploadService {

    public UploadFileResponse uploadCsvFile(MultipartFile file) throws IOException {
        if (!file.getContentType().contentEquals("text/csv"))
            throw new UnsupportedFileTypeException(file.getName() + " " + file.getContentType() + " not supported file type");

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        log.info(fileName + " uploaded file");
        //TODO copy the file to somewhere

        File convFile = new File("/tmp/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return new UploadFileResponse(fileName, "",
                file.getContentType(), file.getSize(), convFile);
    }
}
