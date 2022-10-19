import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    final static int callsQuantity = 60;
    final static int callGenerationTimeInSec = 1;
    final static int timeToTalkInSec = 2;
    final static int secToMillisCoefficient = 1_000;  // no magic numbers:)
    final static int workersNumber = 5;  // no magic numbers:)


    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger processedCallsNumber = new AtomicInteger(0);
        BlockingQueue<String> calls = new ArrayBlockingQueue<>(callsQuantity);

        // атс генерирует звонки:
        new Thread(() -> {
            for (int i = 0; i < callsQuantity; i++) {
                try {
                    calls.put("Звонок номер " + i);
                    System.out.println("Поступил звонок номер " + i);
                    Thread.sleep(callGenerationTimeInSec * secToMillisCoefficient);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        ThreadGroup threadGroup = new ThreadGroup("group of phone workers");
        for (int i = 0; i < workersNumber; i++) {
            new ThreadPhoneWorker(threadGroup,
                    "Оператор " + i, calls,
                    processedCallsNumber, callsQuantity,
                    timeToTalkInSec * secToMillisCoefficient).start();
        }

        while (processedCallsNumber.get() < callsQuantity) {
            continue;
        }
        threadGroup.interrupt();

    }
}
