package org.yourcompany.yourproject.services.filesService;

import java.io.File;

public interface FileService {

    File createFile(String path);

    void writeToFile(String path, String text);

    String readFromFile(String path);

    void clearFile(String path);

    void removeFile(String path);
}
