import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PhoneStation {

    public static void generateCalls(int callsQuantity, BlockingQueue<String> calls, int callGenerationTimeInMillis) {
        // атс генерирует звонки:
        new Thread(() -> {
            for (int i = 0; i < callsQuantity; i++) {
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

    public static void processCall(BlockingQueue<String> calls, AtomicInteger processedCallsNumber,
                                   int timeToTalkInMillis, AtomicInteger workingWorkersCount) {
        workingWorkersCount.incrementAndGet();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " принял " + calls.take());
                processedCallsNumber.incrementAndGet();
                Thread.sleep(timeToTalkInMillis);
                workingWorkersCount.decrementAndGet();
            } catch (InterruptedException e) {
                workingWorkersCount.decrementAndGet();
            }
        }).start();
    }

}
