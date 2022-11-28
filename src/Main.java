import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    protected static final int CALLS_QUANTITY = 60;
    protected static final int CALL_GENERATION_TIME_IN_SEC = 1;
    protected static final int TIME_TO_TALK_IN_SEC = 2;
    protected static final int WORKERS_NUMBER = 5;


    public static void main(String[] args) throws IOException {
        final AtomicInteger processedCallsNumber = new AtomicInteger(0);
        BlockingQueue<String> calls = new ArrayBlockingQueue<>(CALLS_QUANTITY);

        ThreadGroup threadGroup = new ThreadGroup("group");

        for (int i = 1; i < WORKERS_NUMBER + 1; i++) {
            new CallCenterOperator(threadGroup, "Оператор-тред " + i, calls, processedCallsNumber, TIME_TO_TALK_IN_SEC).start();
        }

        PhoneStation.generateCalls(CALLS_QUANTITY, calls, 1000* CALL_GENERATION_TIME_IN_SEC);

        while (processedCallsNumber.get() < CALLS_QUANTITY) {
            continue;
            }
        threadGroup.interrupt();
    }

}
