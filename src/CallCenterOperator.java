import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
class CallCenterOperator extends Thread {

    final AtomicInteger processedCallsNumber;
    final BlockingQueue<String> calls;
    final int timeToTalkInMillis;

    public CallCenterOperator(ThreadGroup threadGroup, String threadName, BlockingQueue<String> calls,
                              AtomicInteger processedCallsNumber,
                              int timeToTalkInSeconds) {
        super(threadGroup, threadName);
        this.calls = calls;
        this.processedCallsNumber = processedCallsNumber;
        this.timeToTalkInMillis = 1000*timeToTalkInSeconds;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println(Thread.currentThread().getName() + " принял " + calls.take());
                processedCallsNumber.incrementAndGet();
                Thread.sleep(timeToTalkInMillis);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
