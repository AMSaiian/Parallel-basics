package FileAnalyze.Shared;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public abstract class TextAnalyzer {
    private final IDocumentSubTaskFactory documentSubTaskFactory;

    protected TextAnalyzer(IDocumentSubTaskFactory documentSubTaskFactory) {
        this.documentSubTaskFactory = documentSubTaskFactory;
    }

    protected void analyze(File rootDirectory) {
        try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
            commonPool.invoke(new FolderSubTask(rootDirectory, documentSubTaskFactory));
        }
    }

    protected abstract void clearPreviousResult();
}
