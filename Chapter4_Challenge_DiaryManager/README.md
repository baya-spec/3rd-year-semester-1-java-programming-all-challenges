# Personal Diary Manager

## Project Overview
The Personal Diary Manager is a command-line application that allows users to write, read, search, and manage their personal diary entries. It demonstrates the use of Java I/O capabilities, specifically the `java.nio.file` package, object serialization, and ZIP file manipulation.

## Features
1.  **Write Entry**: Create new diary entries. Each entry is saved as a separate text file with a timestamped filename (e.g., `diary_2023_10_25_14_30_45.txt`).
2.  **Read Entry**: View a list of all past entries and select one to read its content.
3.  **Search Entries**: Search through all diary entries for a specific keyword.
4.  **Backup**: Create a ZIP archive of all diary entries for backup purposes.
5.  **Settings**: Persist user preferences (e.g., Owner Name) using object serialization.

## How to Run
1.  Compile the Java file:
    ```bash
    javac src/DiaryManager.java
    ```
2.  Run the application:
    ```bash
    java -cp src DiaryManager
    ```
    (Note: Run from the project root so the `entries` folder is created in the correct location relative to execution).

## Design Choices

### File Handling (`java.nio.file`)
-   **Path & Files API**: Used `Paths.get()` and `Files` methods (like `createDirectories`, `newBufferedWriter`, `list`, `readAllLines`) for modern and robust file operations.
-   **Streams**: Used `Files.list()` with Java Streams to filter and sort diary entry files efficiently.

### Input/Output
-   **BufferedWriter**: Used for writing diary entries to ensure efficient writing of characters.
-   **Scanner**: Used for handling user input from the console.

### Data Persistence
-   **Text Files**: Diary entries are stored as plain text files for readability and portability.
-   **Serialization**: The application state (specifically the owner's name) is saved using Java Object Serialization (`diary_config.ser`). This allows the application to remember the user across sessions.

### Backup Mechanism
-   **ZIP Output Stream**: The backup feature utilizes `ZipOutputStream` to compress all text files from the `entries` directory into a single `.zip` file.

## Project Structure
```
Chapter4_Challenge_DiaryManager/
├── src/
│   └── DiaryManager.java
├── entries/                  (Created automatically)
│   ├── diary_YYYY_MM_DD_....txt
├── diary_config.ser          (Created after saving settings)
└── README.md
```
