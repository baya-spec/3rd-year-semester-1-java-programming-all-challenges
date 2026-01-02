package com.diary.util;

import com.diary.model.DiaryEntry;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    private static final String DATA_DIR = "data";

    public FileManager() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEntry(DiaryEntry entry) throws IOException {
        String filename = entry.getId() + ".ser";
        Path path = Paths.get(DATA_DIR, filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(entry);
        }
    }

    public void deleteEntry(DiaryEntry entry) throws IOException {
        String filename = entry.getId() + ".ser";
        Path path = Paths.get(DATA_DIR, filename);
        Files.deleteIfExists(path);
    }

    public List<DiaryEntry> loadAllEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIR))) {
            paths.filter(Files::isRegularFile)
                 .filter(p -> p.toString().endsWith(".ser"))
                 .forEach(p -> {
                     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(p.toFile()))) {
                         entries.add((DiaryEntry) ois.readObject());
                     } catch (IOException | ClassNotFoundException e) {
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
