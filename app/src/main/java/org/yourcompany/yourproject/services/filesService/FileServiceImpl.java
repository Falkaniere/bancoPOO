package org.yourcompany.yourproject.services.filesService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileServiceImpl implements FileService {

    private final String SUB_PATH = "src/main/java/org/yourcompany/yourproject/mocks/";

    /**
     * Create a file if it does not exist in the specified path
     *
     * @param path The path of the file to be created
     * @return The created file
     */
    @Override
    public File createFile(String path) {
        File dir = new File(SUB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * Write text to a file
     *
     * @param path The path of the file to write to
     * @param text
     */
    @Override
    public void writeToFile(String path, String text) {
        File file = new File(SUB_PATH + path);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(text);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read text from a file
     *
     * @param path The path of the file to read from
     * @return The content of the file
     */
    @Override
    public String readFromFile(String path) {
        File file = new File(SUB_PATH + path);

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * Clear a file
     *
     * @param path The path of the file to clear
     */
    @Override
    public void clearFile(String path) {
        File file = new File(SUB_PATH + path);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            // do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a file
     *
     * @param path The path of the file to remove
     */
    @Override
    public void removeFile(String path) {
        File file = new File(SUB_PATH + path);
        if (file.exists()) {
            boolean result = file.delete();
            if (!result) {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}
