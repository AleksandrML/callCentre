import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    protected static final int CALLS_QUANTITY = 60;
    protected static final int CALL_GENERATION_TIME_IN_SEC = 1;
    protected static final int TIME_TO_TALK_IN_SEC = 2;
    protected static final int WORKERS_NUMBER = 5;


    public static void main(String[] args) throws IOException, InterruptedException {
        final AtomicInteger processedCallsNumber = new AtomicInteger(0);
        BlockingQueue<String> calls = new ArrayBlockingQueue<>(CALLS_QUANTITY);

        List<CallCenterOperator> operators = new ArrayList<>();
        for (int i = 1; i < WORKERS_NUMBER + 1; i++) {
            operators.add(new CallCenterOperator(TIME_TO_TALK_IN_SEC, "Оператор номер " + i));
        }

        PhoneStation phoneStation = new PhoneStation(calls, CALL_GENERATION_TIME_IN_SEC, CALLS_QUANTITY,
                processedCallsNumber);
        phoneStation.generateCalls();

        while (processedCallsNumber.get() < CALLS_QUANTITY) {
            phoneStation.passCallToOperator(operators.get(ThreadLocalRandom.current()
                    .nextInt(0, WORKERS_NUMBER)));
            }
    }

}
