import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

public class Clock extends Observable {
  private Timer myTimer;

  private Clock() {
    myTimer = new Timer();
    myTimer.scheduleAtFixedRate(new ClockTask(), 0, 2000);
  }

  // Singleton pattern
  private static Clock uniqueInstanceClock = null;

  public static Clock getInstance() {
    if (uniqueInstanceClock == null) {
      uniqueInstanceClock = new Clock();
    }

    return uniqueInstanceClock;
  }

  public void tick() {
    setChanged();
    notifyObservers(LocalDateTime.now());
  }

  public void deleteTimer() {
    myTimer.cancel();
  }
}
