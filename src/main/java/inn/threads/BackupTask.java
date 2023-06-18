package inn.threads;


import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.concurrent.Task;

public class BackupTask extends Task {
    private MFXProgressBar progressBar;

    public BackupTask(MFXProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected Void call() throws Exception {
        // Perform backup process here
        // ...

        // Simulate backup completion
        for (int i = 1; i <= 100; i++) {
            Thread.sleep(50); // Simulating backup process delay
            updateProgress(i, 100); // Update progress value
        }

        return null;
    }

    public void startBackUpTask() {

    }
}
