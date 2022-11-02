import java.util.TimerTask;

public class ClockTask extends TimerTask {
  //Actualitza el clock i crida al update, que crida al observer.
  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
