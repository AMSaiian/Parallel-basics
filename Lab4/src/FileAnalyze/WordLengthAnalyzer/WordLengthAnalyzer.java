package FileAnalyze.WordLengthAnalyzer;

import FileAnalyze.Shared.TextAnalyzer;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WordLengthAnalyzer extends TextAnalyzer {
    public WordLengthAnalyzer() {
        super(new WordLengthSubTaskFactory());
    }

    public Statistics getWordStatistics(File rootDirectory) {
        clearPreviousResult();
        analyze(rootDirectory);

        double avgWordLength = getAvgWordLength();
        double dispersion = getDispersion();
        double stdDeviation = getStdDeviation();

        return new Statistics(avgWordLength, dispersion, stdDeviation);
    }

    @Override
    protected final void clearPreviousResult() {
        WordLengthSubTask.clearWordLengthCases();
    }

    private double getAvgWordLength() {
        int wordsCount = getWordsCount();

        int wordsLengthSum = WordLengthSubTask.getWordLengthCases().entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue().get())
                .sum();

        return (double)wordsLengthSum / wordsCount;
    }

    private double getDispersion() {
        double average = getAvgWordLength();
        double sumOfSquaredDeviations = 0;
        for (Map.Entry<Integer, AtomicInteger> entry :  WordLengthSubTask.getWordLengthCases().entrySet()) {
            sumOfSquaredDeviations += Math.pow(entry.getKey() - average, 2) * entry.getValue().get();
        }
        return Math.round(
                sumOfSquaredDeviations / getWordsCount() * Math.pow(10, 1)) / Math.pow(10, 1);
    }

    private int getWordsCount() {
        return WordLengthSubTask.getWordLengthCases().values()
                .stream()
                .mapToInt(AtomicInteger::get)
                .sum();
    }

    private double getStdDeviation() {
        double dispersion = getDispersion();
        return Math.round(
                Math.sqrt(dispersion) * Math.pow(10, 1)) / Math.pow(10, 1);
    }
}
