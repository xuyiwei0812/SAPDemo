import java.util.Timer;

/**
 * 从SAP中定时查询并输出
 */
public class PeriodDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new DoSomethingTimerTask("PeriodDemo"), 2000L, 1000L);
    }
}
