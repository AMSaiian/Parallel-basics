package FileAnalyze.Shared;

import java.io.File;

public interface IDocumentSubTaskFactory {
    public DocumentSubTask getDocumentSubTask(File document);
}
