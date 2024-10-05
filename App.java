import java.io.*;
import java.util.*;

class Note {
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nContent: " + content;
    }
}

class Notebook {
    private List<Note> notes;

    public Notebook() {
        notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void deleteNote(String title) {
        Note noteToRemove = null;
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                noteToRemove = note;
                break;
            }
        }
        if (noteToRemove != null) {
            notes.remove(noteToRemove);
            System.out.println("Note '" + title + "' deleted.");
        } else {
            System.out.println("Note not found.");
        }
    }

    public void editNote(String title, String newContent) {
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                note.setContent(newContent);
                System.out.println("Note '" + title + "' updated.");
                return;
            }
        }
        System.out.println("Note not found.");
    }

    public void listNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
        } else {
            for (Note note : notes) {
                System.out.println(note);
                System.out.println("----------");
            }
        }
    }

    public Note findNoteByTitle(String title) {
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                return note;
            }
        }
        return null;
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Note note : notes) {
                writer.write(note.getTitle());
                writer.newLine();
                writer.write(note.getContent());
                writer.newLine();
                writer.write("-----");
                writer.newLine();
            }
        }
        System.out.println("Notes saved to " + filename);
    }

    public void loadFromFile(String filename) throws IOException {
        notes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String title = null;
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("-----")) {
                    if (title != null) {
                        notes.add(new Note(title, content.toString().trim()));
                    }
                    title = null;
                    content = new StringBuilder();
                } else if (title == null) {
                    title = line;
                } else {
                    content.append(line).append("\n");
                }
            }
        }
        System.out.println("Notes loaded from " + filename);
    }
}

public class NoteApp {

    private static Scanner scanner = new Scanner(System.in);
    private static Notebook notebook = new Notebook();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Notebook Application");
            System.out.println("1. Add Note");
            System.out.println("2. Delete Note");
            System.out.println("3. Edit Note");
            System.out.println("4. List Notes");
            System.out.println("5. Save Notes");
            System.out.println("6. Load Notes");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addNote();
                    break;
                case 2:
                    deleteNote();
                    break;
                case 3:
                    editNote();
                    break;
                case 4:
                    notebook.listNotes();
                    break;
                case 5:
                    saveNotes();
                    break;
                case 6:
                    loadNotes();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addNote() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();
        notebook.addNote(new Note(title, content));
        System.out.println("Note added.");
    }

    private static void deleteNote() {
        System.out.print("Enter title of the note to delete: ");
        String title = scanner.nextLine();
        notebook.deleteNote(title);
    }

    private static void editNote() {
        System.out.print("Enter title of the note to edit: ");
        String title = scanner.nextLine();
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();
        notebook.editNote(title, newContent);
    }

    private static void saveNotes() {
        System.out.print("Enter filename to save notes: ");
        String filename = scanner.nextLine();
        try {
            notebook.saveToFile(filename);
        } catch (IOException e) {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }

    private static void loadNotes() {
        System.out.print("Enter filename to load notes: ");
        String filename = scanner.nextLine();
        try {
            notebook.loadFromFile(filename);
        } catch (IOException e) {
            System.out.println("Error loading notes: " + e.getMessage());
        }
    }
};
