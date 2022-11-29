
public class CallCenterOperator {

    protected final int timeToTalkInMillis;
    protected final String operatorName;

    public CallCenterOperator(int timeToTalkInSeconds, String operatorName) {
        this.timeToTalkInMillis = 1000*timeToTalkInSeconds;
        this.operatorName = operatorName;
    }

    public void processCall(String call) {
        new Thread(() -> {
            try {
                System.out.println(operatorName + " принял " + call);
                Thread.sleep(timeToTalkInMillis);
            } catch (InterruptedException e) {
                System.out.println(operatorName + " был прерван на звонке " + call);
            }
        }).start();
    }

}
