package inn.threads;

import javafx.concurrent.Task;

public class NumberCounterTask extends Task<Long> {

    private final long maxValue;

    public NumberCounterTask(long maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected Long call() throws Exception {
        long sumOfValues = 0;
        for(int x =0; x < maxValue; x++) {
            sumOfValues += x;
            updateValue(sumOfValues);
            updateProgress(x, maxValue);
        }
        return sumOfValues;
    }
}
