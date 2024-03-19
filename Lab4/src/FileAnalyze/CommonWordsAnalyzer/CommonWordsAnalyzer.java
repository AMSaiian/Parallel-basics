package FileAnalyze.CommonWordsAnalyzer;

import FileAnalyze.Shared.IDocumentSubTaskFactory;
import FileAnalyze.Shared.TextAnalyzer;

import java.io.File;
import java.util.ArrayList;

public class CommonWordsAnalyzer extends TextAnalyzer {
    public CommonWordsAnalyzer() {
        super(new CommonWordsSubTaskFactory());
    }

    public ArrayList<String> getCommonWords(File rootDirectory) {
        clearPreviousResult();
        analyze(rootDirectory);

        return CommonWordsSubTask.getCommonWords();
    }

    @Override
    protected void clearPreviousResult() {
        CommonWordsSubTask.clearCommonWords();
    }
}
