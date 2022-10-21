import java.time.LocalDateTime;
import java.util.Observable;

public class TimeController extends Observable {
  private static TimeController uniqueInstanceTimeController = null;
  public LocalDateTime dateTimeNow;

  private TimeController() {
    dateTimeNow = LocalDateTime.now();
  }

  public static TimeController getInstance() {
    if (uniqueInstanceTimeController == null) {
      uniqueInstanceTimeController = new TimeController();
    }

    return uniqueInstanceTimeController;
  }

  private void tick() {
    dateTimeNow = LocalDateTime.now();
    setChanged();
    notifyObservers(dateTimeNow);
  }

  public void updateTime() throws InterruptedException {
    while (true) {
      tick();
      Thread.sleep(2000);
    }
  }
}
