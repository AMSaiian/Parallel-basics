package FileAnalyze.CommonWordsAnalyzer;

import FileAnalyze.Shared.DocumentSubTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CommonWordsSubTask extends DocumentSubTask {
    private static final Set<String> commonWords = ConcurrentHashMap.newKeySet();
    public CommonWordsSubTask(File document) {
        super(document);
    }

    @Override
    protected void compute() {
        ArrayList<String> words = getWords();

        synchronized (commonWords) {
            if (commonWords.isEmpty())
                commonWords.addAll(words);
            else
                commonWords.retainAll(words);
        }
    }

    public static void clearCommonWords() {
        commonWords.clear();
    }

    public static ArrayList<String> getCommonWords() {
        return new ArrayList<>(commonWords);
    }
}
