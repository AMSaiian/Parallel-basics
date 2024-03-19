package FileAnalyze.KeywordsAnalyzer;

import FileAnalyze.Shared.IDocumentSubTaskFactory;
import FileAnalyze.Shared.TextAnalyzer;

import java.io.File;
import java.util.ArrayList;

public class KeywordsAnalyzer extends TextAnalyzer {
    public KeywordsAnalyzer() {
        super(new KeywordsSubTaskFactory());
    }

    public ArrayList<String> getMatchedFilesByKeyWords(File rootDirectory, ArrayList<String> keyWords) {
        clearPreviousResult();
        KeywordsSubTask.setSearchedKeyWords(keyWords);

        analyze(rootDirectory);

        return KeywordsSubTask.getMatchedFiles();
    }

    @Override
    protected void clearPreviousResult() {
        KeywordsSubTask.clearState();
    }
}
