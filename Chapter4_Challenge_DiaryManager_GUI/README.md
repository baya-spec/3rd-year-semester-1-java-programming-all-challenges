# My Diary - Personal Diary Manager

A modern, minimalist personal diary application built with JavaFX. This application allows users to write, organize, and search their personal thoughts and gratitude entries in a serene, wellness-focused interface.

## Features

### 1. GUI Interface
- **Modern Dashboard**: Clean layout with a navigation sidebar and main content area.
- **Entry Editor**: Rich text editing capabilities (HTML) for expressive writing.
- **Entry Browser**: Visual list of diary entries with previews in the sidebar.
- **Search Panel**: Real-time search functionality to filter entries by title or content.
- **Responsive Design**: Adapts to window resizing.

### 2. Core Functionality
- **Write Mode**: Create new entries with a rich text editor.
- **Read Mode**: View formatted entries.
- **Search**: Filter entries instantly as you type.
- **File Operations**: Auto-save (on button click), delete, and organize entries.
- **Persistence**: Entries are saved as serialized objects in a local `data` directory.

### 3. Technical Highlights
- **JavaFX**: Utilizes `BorderPane`, `VBox`, `HBox`, `ListView`, `HTMLEditor`, and more.
- **CSS Styling**: Custom `styles.css` implementing a soft pastel blue and teal color palette.
- **File I/O**: Uses `java.nio.file` for robust file handling and `ObjectOutputStream`/`ObjectInputStream` for serialization.
- **MVC Pattern**: Separation of concerns with `DiaryEntry` (Model), `DiaryApplication` (View/Controller), and `FileManager` (Data Access).

## Design Choices

- **Color Palette**: 
    - Sidebar: Soft pastel blue (#A0D8EF)
    - Accents: Teal/Cyan (#40E0D0 to #00BFA5)
    - Background: Pure white (#FFFFFF) with light gray cards (#FAFAFA)
    - This combination was chosen to evoke a sense of calm and clarity, suitable for a personal diary.
- **User Experience**: 
    - The sidebar provides quick access to recent entries.
    - The "New Entry" button is prominent.
    - Search is always accessible at the top.
    - Rich text editing allows for bold, italics, and other formatting to make entries more personal.

## How to Use

1. **Launch the Application**: terminal "mvn clean javafx:run"`.
2. **Create an Entry**: Click "New Entry", enter a title, write your thoughts in the editor, and click "Save".
3. **View an Entry**: Click on any entry in the sidebar list to view and edit it.
4. **Search**: Type in the search bar at the top to filter the list of entries.
5. **Delete**: Select an entry and click "Delete" to remove it permanently.

## Requirements

- Java Development Kit (JDK) 11 or higher (requires JavaFX).
- JavaFX SDK (if not bundled with JDK).

## Project Structure

- `src/com/diary/model/DiaryEntry.java`: Data model for a diary entry.
- `src/com/diary/util/FileManager.java`: Handles file I/O operations.
- `src/com/diary/DiaryApplication.java`: Main JavaFX application class.
- `src/styles.css`: Stylesheet for the application.
