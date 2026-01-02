import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DiaryManager {
    private static final String ENTRIES_DIR = "entries";
    private static final String CONFIG_FILE = "diary_config.ser";
    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
    
    private static DiaryConfiguration config;

    public static void main(String[] args) {
        ensureEntriesDirectoryExists();
        loadConfiguration();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome " + (config.getOwnerName() != null ? config.getOwnerName() : "User"));

        while (true) {
            System.out.println("\n--- Personal Diary Manager ---");
            System.out.println("1. Write Entry");
            System.out.println("2. Read Entry");
            System.out.println("3. Search Entries");
            System.out.println("4. Backup Entries (ZIP)");
            System.out.println("5. Settings (Set Owner Name)");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    writeEntry(scanner);
                    break;
                case "2":
                    readEntry(scanner);
                    break;
                case "3":
                    searchEntries(scanner);
                    break;
                case "4":
                    backupEntries();
                    break;
                case "5":
                    updateSettings(scanner);
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void ensureEntriesDirectoryExists() {
        try {
            Files.createDirectories(Paths.get(ENTRIES_DIR));
        } catch (IOException e) {
            System.err.println("Error creating entries directory: " + e.getMessage());
        }
    }

    private static void loadConfiguration() {
        Path configPath = Paths.get(CONFIG_FILE);
        if (Files.exists(configPath)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(configPath))) {
                config = (DiaryConfiguration) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading configuration: " + e.getMessage());
                config = new DiaryConfiguration();
            }
        } else {
            config = new DiaryConfiguration();
        }
    }

    private static void saveConfiguration() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(CONFIG_FILE)))) {
            oos.writeObject(config);
            System.out.println("Configuration saved.");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    private static void updateSettings(Scanner scanner) {
        System.out.print("Enter owner name: ");
        String name = scanner.nextLine();
        config.setOwnerName(name);
        saveConfiguration();
    }

    private static void writeEntry(Scanner scanner) {
        System.out.println("\nWrite your diary entry (press Enter on an empty line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
            content.append(line).append(System.lineSeparator());
        }

        if (content.length() == 0) {
            System.out.println("Empty entry discarded.");
            return;
        }

        String timestamp = LocalDateTime.now().format(FILE_NAME_FORMATTER);
        String filename = "diary_" + timestamp + ".txt";
        Path filePath = Paths.get(ENTRIES_DIR, filename);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(content.toString());
            System.out.println("Entry saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing entry: " + e.getMessage());
        }
    }

    private static void readEntry(Scanner scanner) {
        try (Stream<Path> paths = Files.list(Paths.get(ENTRIES_DIR))) {
            List<Path> files = paths.filter(Files::isRegularFile)
                                    .sorted()
                                    .collect(Collectors.toList());

            if (files.isEmpty()) {
                System.out.println("No entries found.");
                return;
            }

            System.out.println("\nAvailable Entries:");
            for (int i = 0; i < files.size(); i++) {
                System.out.println((i + 1) + ". " + files.get(i).getFileName());
            }

            System.out.print("Select an entry number to read: ");
            try {
                String input = scanner.nextLine();
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < files.size()) {
                    Path selectedFile = files.get(index);
                    System.out.println("\n--- " + selectedFile.getFileName() + " ---");
                    Files.lines(selectedFile).forEach(System.out::println);
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

        } catch (IOException e) {
            System.err.println("Error listing entries: " + e.getMessage());
        }
    }

    private static void searchEntries(Scanner scanner) {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        try (Stream<Path> paths = Files.list(Paths.get(ENTRIES_DIR))) {
            List<Path> files = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            boolean found = false;

            for (Path file : files) {
                try {
                    List<String> lines = Files.readAllLines(file);
                    for (String line : lines) {
                        if (line.toLowerCase().contains(keyword)) {
                            System.out.println("Found in " + file.getFileName() + ": " + line.trim());
                            found = true;
                            break; // Show file once if found
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file " + file.getFileName() + ": " + e.getMessage());
                }
            }

            if (!found) {
                System.out.println("No entries found containing '" + keyword + "'.");
            }

        } catch (IOException e) {
            System.err.println("Error searching entries: " + e.getMessage());
        }
    }

    private static void backupEntries() {
        String timestamp = LocalDateTime.now().format(FILE_NAME_FORMATTER);
        String zipFileName = "backup_" + timestamp + ".zip";
        Path zipPath = Paths.get(zipFileName);
        Path sourceDir = Paths.get(ENTRIES_DIR);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath));
             Stream<Path> paths = Files.walk(sourceDir)) {

            paths.filter(path -> !Files.isDirectory(path))
                 .forEach(path -> {
                     ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                     try {
                         zos.putNextEntry(zipEntry);
                         Files.copy(path, zos);
                         zos.closeEntry();
                     } catch (IOException e) {
                         System.err.println("Error adding file to zip: " + path);
                     }
                 });

            System.out.println("Backup created successfully: " + zipFileName);

        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    // Serializable Configuration Class
    static class DiaryConfiguration implements Serializable {
        private static final long serialVersionUID = 1L;
        private String ownerName;

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
    }
}
