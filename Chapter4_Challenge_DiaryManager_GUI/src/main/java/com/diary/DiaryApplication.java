package com.diary;

import com.diary.model.DiaryEntry;
import com.diary.util.FileManager;
import com.diary.util.SampleDataGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class DiaryApplication extends Application {

    private FileManager fileManager;
    private ObservableList<DiaryEntry> entries;
    private FilteredList<DiaryEntry> filteredEntries;
    private ListView<DiaryEntry> entryListView;
    private TextField titleField;
    private HTMLEditor contentEditor;
    private DiaryEntry currentEntry;
    private Label statusLabel;
    private BorderPane root;
    private boolean isDarkMode = false;
    private DatePicker calendarPicker;

    @Override
    public void start(Stage primaryStage) {
        fileManager = new FileManager();
        
        // Generate sample data if empty
        SampleDataGenerator.generateSampleData(fileManager);

        entries = FXCollections.observableArrayList();
        filteredEntries = new FilteredList<>(entries, p -> true);

        root = new BorderPane();
        root.getStyleClass().add("root-pane");

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Main Content
        BorderPane mainContent = new BorderPane();
        mainContent.setPadding(new Insets(20));
        
        // Top Bar (Search & New Entry)
        HBox topBar = createTopBar();
        mainContent.setTop(topBar);

        // Editor Area
        VBox editorArea = createEditorArea();
        mainContent.setCenter(editorArea);

        root.setCenter(mainContent);

        // Status Bar
        statusLabel = new Label("Ready");
        statusLabel.getStyleClass().add("status-label");
        HBox statusBar = new HBox(statusLabel);
        statusBar.setPadding(new Insets(5));
        statusBar.getStyleClass().add("status-bar");
        statusBar.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #E0E0E0; -fx-border-width: 1 0 0 0;");
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("My Diary");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load entries in background
        loadEntriesInBackground();
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(300);
        sidebar.getStyleClass().add("sidebar");

        Label appTitle = new Label("My Diary");
        appTitle.getStyleClass().add("app-title");

        // Calendar (DatePicker)
        Label calendarLabel = new Label("📅 Calendar");
        calendarLabel.getStyleClass().add("section-header");
        calendarPicker = new DatePicker(LocalDate.now());
        calendarPicker.setMaxWidth(Double.MAX_VALUE);
        calendarPicker.setOnAction(e -> filterByDate(calendarPicker.getValue()));

        // Navigation Buttons
        Button btnAllEntries = createNavButton("📚 All Entries");
        btnAllEntries.setOnAction(e -> {
            calendarPicker.setValue(null);
            filteredEntries.setPredicate(p -> true);
        });

        Button btnSettings = createNavButton("⚙️ Toggle Theme");
        btnSettings.setOnAction(e -> toggleTheme());
        
        // Entry List
        Label entriesLabel = new Label("📝 Timeline");
        entriesLabel.getStyleClass().add("section-header");
        
        entryListView = new ListView<>(filteredEntries);
        entryListView.setCellFactory(new Callback<ListView<DiaryEntry>, ListCell<DiaryEntry>>() {
            @Override
            public ListCell<DiaryEntry> call(ListView<DiaryEntry> param) {
                return new ListCell<DiaryEntry>() {
                    @Override
                    protected void updateItem(DiaryEntry item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                            setStyle("-fx-background-color: transparent;");
                        } else {
                            VBox card = new VBox(5);
                            Label title = new Label(item.getTitle());
                            title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                            
                            Label date = new Label(item.getFormattedDate());
                            date.setStyle("-fx-opacity: 0.7; -fx-font-size: 11px;");
                            
                            // Preview content (stripped of HTML tags for list view)
                            String previewText = item.getContent().replaceAll("<[^>]*>", "").trim();
                            if (previewText.length() > 60) previewText = previewText.substring(0, 60) + "...";
                            Label preview = new Label(previewText);
                            preview.setWrapText(true);
                            preview.setStyle("-fx-font-size: 12px; -fx-opacity: 0.9;");

                            card.getChildren().addAll(title, date, preview);
                            setGraphic(card);
                        }
                    }
                };
            }
        });
        entryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadEntry(newVal);
            }
        });
        VBox.setVgrow(entryListView, Priority.ALWAYS);

        sidebar.getChildren().addAll(appTitle, calendarLabel, calendarPicker, btnAllEntries, btnSettings, new Separator(), entriesLabel, entryListView);
        return sidebar;
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("nav-button");
        return btn;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 20, 0));

        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search entries...");
        searchField.setPrefWidth(300);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEntries.setPredicate(entry -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (entry.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (entry.getContent().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button newEntryBtn = new Button("➕ New Entry");
        newEntryBtn.getStyleClass().add("action-button");
        newEntryBtn.setOnAction(e -> clearEditor());

        Button saveBtn = new Button("💾 Save");
        saveBtn.getStyleClass().add("action-button");
        saveBtn.setOnAction(e -> saveCurrentEntry());

        Button deleteBtn = new Button("🗑️ Delete");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> deleteCurrentEntry());

        topBar.getChildren().addAll(searchField, spacer, newEntryBtn, saveBtn, deleteBtn);
        return topBar;
    }

    private VBox createEditorArea() {
        VBox editorArea = new VBox(15);
        
        titleField = new TextField();
        titleField.setPromptText("Entry Title");
        titleField.getStyleClass().add("entry-title-field");

        contentEditor = new HTMLEditor();
        contentEditor.setPrefHeight(600);
        VBox.setVgrow(contentEditor, Priority.ALWAYS);
        
        editorArea.getChildren().addAll(titleField, contentEditor);
        return editorArea;
    }

    private void loadEntriesInBackground() {
        Task<List<DiaryEntry>> loadTask = new Task<>() {
            @Override
            protected List<DiaryEntry> call() {
                return fileManager.loadAllEntries();
            }
        };

        loadTask.setOnSucceeded(e -> {
            entries.setAll(loadTask.getValue());
            entries.sort(Comparator.comparing(DiaryEntry::getCreatedDate).reversed());
            statusLabel.setText("Entries loaded.");
        });

        loadTask.setOnFailed(e -> {
            statusLabel.setText("Failed to load entries.");
            loadTask.getException().printStackTrace();
        });

        new Thread(loadTask).start();
    }

    private void loadEntry(DiaryEntry entry) {
        currentEntry = entry;
        titleField.setText(entry.getTitle());
        contentEditor.setHtmlText(entry.getContent());
        statusLabel.setText("Loaded: " + entry.getTitle());
    }

    private void clearEditor() {
        currentEntry = null;
        titleField.clear();
        contentEditor.setHtmlText("");
        entryListView.getSelectionModel().clearSelection();
        statusLabel.setText("New Entry Mode");
    }

    private void saveCurrentEntry() {
        String title = titleField.getText();
        String content = contentEditor.getHtmlText();

        if (title.isEmpty()) {
            showAlert("Error", "Title cannot be empty.");
            return;
        }

        statusLabel.setText("Saving...");

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (currentEntry == null) {
                    DiaryEntry newEntry = new DiaryEntry(title, content);
                    fileManager.saveEntry(newEntry);
                    Platform.runLater(() -> {
                        entries.add(0, newEntry);
                        currentEntry = newEntry;
                    });
                } else {
                    currentEntry.setTitle(title);
                    currentEntry.setContent(content);
                    fileManager.saveEntry(currentEntry);
                    Platform.runLater(() -> entryListView.refresh());
                }
                return null;
            }
        };

        saveTask.setOnSucceeded(e -> statusLabel.setText("Saved: " + title));
        saveTask.setOnFailed(e -> {
            statusLabel.setText("Error saving entry.");
            showAlert("Error", "Could not save entry: " + saveTask.getException().getMessage());
        });

        new Thread(saveTask).start();
    }

    private void deleteCurrentEntry() {
        if (currentEntry != null) {
            DiaryEntry entryToDelete = currentEntry;
            statusLabel.setText("Deleting...");
            
            Task<Void> deleteTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    fileManager.deleteEntry(entryToDelete);
                    return null;
                }
            };

            deleteTask.setOnSucceeded(e -> {
                entries.remove(entryToDelete);
                clearEditor();
                statusLabel.setText("Entry deleted.");
            });

            deleteTask.setOnFailed(e -> {
                statusLabel.setText("Error deleting entry.");
                showAlert("Error", "Could not delete entry: " + deleteTask.getException().getMessage());
            });

            new Thread(deleteTask).start();
        }
    }

    private void filterByDate(LocalDate date) {
        if (date == null) {
            filteredEntries.setPredicate(p -> true);
        } else {
            filteredEntries.setPredicate(entry -> 
                entry.getCreatedDate().toLocalDate().equals(date));
        }
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            root.getStyleClass().add("dark-mode");
        } else {
            root.getStyleClass().remove("dark-mode");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
