package FileAnalyze.KeywordsAnalyzer;

import FileAnalyze.Shared.IDocumentSubTaskFactory;

import java.io.File;

public class KeywordsSubTaskFactory implements IDocumentSubTaskFactory {
    @Override
    public KeywordsSubTask getDocumentSubTask(File document) {
        return new KeywordsSubTask(document);
    }
}
