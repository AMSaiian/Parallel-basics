package FileAnalyze.Shared;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public abstract class DocumentSubTask extends RecursiveAction {
    protected File document;

    public DocumentSubTask(File document) {
        if (!document.isFile())
            throw new IllegalArgumentException("Document sub task can be created only with document");

        this.document = document;
    }

    protected ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(document))) {
            String line;

            while ((line = reader.readLine()) != null) {
                words.addAll(Arrays.asList(
                        line.trim().split("\\s+|\\p{Punct}+")));
            }

            words.removeIf(String::isEmpty);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return words;
    }
}
