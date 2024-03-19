package FileAnalyze.KeywordsAnalyzer;

import FileAnalyze.Shared.DocumentSubTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class KeywordsSubTask extends DocumentSubTask {
    private static final Set<String> matchedFiles = ConcurrentHashMap.newKeySet();
    private static ArrayList<String> searchedKeyWords = new ArrayList<>();

    public KeywordsSubTask(File document) {
        super(document);
    }

    @Override
    protected void compute() {
        ArrayList<String> words = getWords();
        words.retainAll(searchedKeyWords);

        if (!words.isEmpty())
            matchedFiles.add(document.getName());
    }

    public static void setSearchedKeyWords(ArrayList<String> searchedKeyWords) {
        KeywordsSubTask.searchedKeyWords = searchedKeyWords;
    }

    public static void clearState() {
        KeywordsSubTask.matchedFiles.clear();
        KeywordsSubTask.searchedKeyWords.clear();
    }

    public static ArrayList<String> getMatchedFiles() {
        return new ArrayList<>(KeywordsSubTask.matchedFiles);
    }
}
