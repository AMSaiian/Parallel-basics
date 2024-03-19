package FileAnalyze.CommonWordsAnalyzer;

import FileAnalyze.Shared.DocumentSubTask;
import FileAnalyze.Shared.IDocumentSubTaskFactory;

import java.io.File;
import java.util.ArrayList;

public class CommonWordsSubTaskFactory implements IDocumentSubTaskFactory {
    @Override
    public CommonWordsSubTask getDocumentSubTask(File document) {
        return new CommonWordsSubTask(document);
    }
}
