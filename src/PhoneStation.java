import java.util.concurrent.BlockingQueue;

public class PhoneStation {

    public static void generateCalls(int callsQuantity, BlockingQueue<String> calls, int callGenerationTimeInMillis) {
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

}
