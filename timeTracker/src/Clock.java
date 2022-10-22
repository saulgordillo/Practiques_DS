import java.time.LocalDateTime;
import java.util.Observable;

public class Clock extends Observable {
  private LocalDateTime dateTimeNow;

  private Clock() {

  }

  // Singleton pattern
  private static Clock uniqueInstanceClock = null;

  public static Clock getInstance() {
    if (uniqueInstanceClock == null) {
      uniqueInstanceClock = new Clock();
    }

    return uniqueInstanceClock;
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
