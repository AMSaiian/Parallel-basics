package FileAnalyze.Shared;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class FolderSubTask extends RecursiveAction {
    private final ArrayList<File> subFolders = new ArrayList<>();
    private final ArrayList<File> subDocuments = new ArrayList<>();
    private final IDocumentSubTaskFactory factory;

    public FolderSubTask(File folder, IDocumentSubTaskFactory factory) {
        this.factory = factory;

        if (!folder.isDirectory())
            throw new IllegalArgumentException("Folder sub task can be created only with folder");

        for (File entry : folder.listFiles()) {
            if (entry.isDirectory()) {
                subFolders.add(entry);
            } else {
                subDocuments.add(entry);
            }
        }
    }

    @Override
    protected void compute() {
        ArrayList<RecursiveAction> forks = new ArrayList<>();
        for (File subFolder : subFolders) {
            FolderSubTask task = new FolderSubTask(subFolder, factory);
            forks.add(task);
            task.fork();
        }

        for (File document : subDocuments) {
            DocumentSubTask task = factory.getDocumentSubTask(document);
            forks.add(task);
            task.fork();
        }

        for (RecursiveAction task : forks) {
            task.join();
        }
    }
}
