package FileAnalyze.WordLengthAnalyzer;

import FileAnalyze.Shared.DocumentSubTask;
import FileAnalyze.Shared.IDocumentSubTaskFactory;

import java.io.File;

public class WordLengthSubTaskFactory implements IDocumentSubTaskFactory {
    @Override
    public DocumentSubTask getDocumentSubTask(File document) {
        return new WordLengthSubTask(document);
    }
}
