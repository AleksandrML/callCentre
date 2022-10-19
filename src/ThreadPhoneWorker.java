import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

class ThreadPhoneWorker extends Thread {

    final AtomicInteger processedCallsNumber;
    final int callsQuantity;
    final int timeToTalk;
    final BlockingQueue<String> calls;

    public ThreadPhoneWorker(ThreadGroup threadGroup, String threadName, BlockingQueue<String> calls,
                             AtomicInteger processedCallsNumber, int callsQuantity, int timeToTalk) {
        super(threadGroup, threadName);
        this.calls = calls;
        this.processedCallsNumber = processedCallsNumber;
        this.callsQuantity = callsQuantity;
        this.timeToTalk = timeToTalk;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println(Thread.currentThread().getName() + " принял " + calls.take());
                processedCallsNumber.incrementAndGet();
                Thread.sleep(timeToTalk);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
