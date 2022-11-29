import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PhoneStation {

    protected final BlockingQueue<String> calls;
    protected final AtomicInteger processedCallsNumber;
    protected final int callGenerationTimeInMillis;
    protected final int callsQuantity;

    public PhoneStation(BlockingQueue<String> calls, int callGenerationTimeInSeconds,
                        int callsQuantity, AtomicInteger processedCallsNumber) {
        this.calls = calls;
        this.callGenerationTimeInMillis = 1000*callGenerationTimeInSeconds;
        this.callsQuantity = callsQuantity;
        this.processedCallsNumber = processedCallsNumber;
    }
    public void generateCalls() {
        // атс генерирует звонки:
        new Thread(() -> {
            for (int i = 1; i < callsQuantity + 1; i++) {
                try {
                    calls.put("Звонок номер " + i);
                    System.out.println("Поступил звонок номер " + i);
                    Thread.sleep(callGenerationTimeInMillis);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    public void passCallToOperator(CallCenterOperator operator) throws InterruptedException {
        operator.processCall(calls.take());
        processedCallsNumber.incrementAndGet();
    }

}
