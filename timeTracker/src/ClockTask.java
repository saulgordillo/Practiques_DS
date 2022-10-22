import java.util.TimerTask;

public class ClockTask extends TimerTask {
  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
