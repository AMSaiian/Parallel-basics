package FileAnalyze.WordLengthAnalyzer;

import FileAnalyze.Shared.DocumentSubTask;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WordLengthSubTask extends DocumentSubTask {
    private static final ConcurrentHashMap<Integer, AtomicInteger> wordLengthCases = new ConcurrentHashMap<>();
    public WordLengthSubTask(File document) {
        super(document);
    }

    @Override
    protected void compute() {
        for (String word : getWords()) {
            wordLengthCases.computeIfAbsent(word.length(), k -> new AtomicInteger(0));
            wordLengthCases.get(word.length()).getAndIncrement();
        }
    }

    public static void clearWordLengthCases() {
        wordLengthCases.clear();
    }

    public static ConcurrentHashMap<Integer, AtomicInteger> getWordLengthCases() {
        return wordLengthCases;
    }
}
