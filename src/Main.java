import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    protected static final int callsQuantity = 60;
    protected static final int callGenerationTimeInSec = 1;
    protected static final int timeToTalkInSec = 2;
    protected static final int secToMillisCoefficient = 1_000;  // no magic numbers:)


    public static void main(String[] args) {
        final AtomicInteger processedCallsNumber = new AtomicInteger(0);
        BlockingQueue<String> calls = new ArrayBlockingQueue<>(callsQuantity);

        PhoneStation.generateCalls(callsQuantity, calls, callGenerationTimeInSec * secToMillisCoefficient);

        int threadsCreated = 0;
        while (processedCallsNumber.get() < callsQuantity && threadsCreated < callsQuantity) {
            PhoneStation.processCall(calls, processedCallsNumber, timeToTalkInSec * secToMillisCoefficient);
            threadsCreated += 1;
        }

    }
}
