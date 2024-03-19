package FileAnalyze;

import FileAnalyze.CommonWordsAnalyzer.CommonWordsAnalyzer;
import FileAnalyze.KeywordsAnalyzer.KeywordsAnalyzer;
import FileAnalyze.WordLengthAnalyzer.Statistics;
import FileAnalyze.WordLengthAnalyzer.WordLengthAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static final String FILE_PATH_PREFIX = "./src/FileAnalyze/Data/";

    public static void main(String[] args) {
        testWordLength();
        testCommonWords();
        testKeyWords();
    }

    private static void testWordLength() {
        String rootFolderName = FILE_PATH_PREFIX + "1";
        File rootFolder = new File(rootFolderName);

        WordLengthAnalyzer analyzer = new WordLengthAnalyzer();

        long startTimeParallel = System.currentTimeMillis();
        Statistics resultParallel = analyzer.getWordStatistics(rootFolder);
        long endTimeParallel = System.currentTimeMillis();

        System.out.println("\nWords characteristics analyzer result\nParallel correctness:");
        System.out.println(resultParallel);
        System.out.printf("Execution time in ms: %d%n", endTimeParallel - startTimeParallel);

        rootFolderName = FILE_PATH_PREFIX + "2";
        rootFolder = new File(rootFolderName);

        startTimeParallel = System.currentTimeMillis();
        resultParallel = analyzer.getWordStatistics(rootFolder);
        endTimeParallel = System.currentTimeMillis();

        System.out.println("\nWords characteristics analyzer result\nParallel speed:");
        System.out.println(resultParallel);
        System.out.printf("Execution time in ms: %d%n", endTimeParallel - startTimeParallel);
    }

    private static void testCommonWords() {
        String rootFolderName = FILE_PATH_PREFIX + "Common";
        File rootFolder = new File(rootFolderName);

        CommonWordsAnalyzer analyzer = new CommonWordsAnalyzer();

        long startTime = System.currentTimeMillis();
        ArrayList<String> result = analyzer.getCommonWords(rootFolder);
        long endTime = System.currentTimeMillis();

        System.out.println("\nCommon words analyzer result\nCommon words");
        System.out.println(result.toString());
        System.out.printf("Execution time in ms: %d%n", endTime - startTime);
    }

    private static void testKeyWords() {
        String rootFolderName = FILE_PATH_PREFIX + "IT";
        File rootFolder = new File(rootFolderName);

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
                "software", "hardware", "programming", "language", "computer",
                "algorithm", "data", "structure", "science", "technology",
                "internet", "network", "database"
        ));

        ArrayList<String> singleKeyWord = new ArrayList<>(Arrays.asList("network"));

        KeywordsAnalyzer analyzer = new KeywordsAnalyzer();

        long startTime = System.currentTimeMillis();
        ArrayList<String> result = analyzer.getMatchedFilesByKeyWords(rootFolder, keywords);
        long endTime = System.currentTimeMillis();

        System.out.println("\nKeywords analyzer result\nMatched files");
        System.out.println(result.toString());
        System.out.printf("Execution time in ms: %d%n", endTime - startTime);

        startTime = System.currentTimeMillis();
        result = analyzer.getMatchedFilesByKeyWords(rootFolder, singleKeyWord);
        endTime = System.currentTimeMillis();

        System.out.println("\nKeywords analyzer result with single matched file\nMatched files");
        System.out.println(result.toString());
        System.out.printf("Execution time in ms: %d%n", endTime - startTime);
    }
}