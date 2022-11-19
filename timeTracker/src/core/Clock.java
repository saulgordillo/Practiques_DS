package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

public class Clock extends Observable {
	
  static Logger loggerClock = LoggerFactory.getLogger("core.Observable.Clock");
  // Singleton pattern
  private static Clock uniqueInstanceClock = null;
  private final Timer myTimer;

  /**
   * Set a timer to execute the run() method in ClockTimer class every 2 seconds (2000 milliseconds)
   */
  private Clock() {
    myTimer = new Timer();
    myTimer.scheduleAtFixedRate(new ClockTask(), 0, 2000);
  }

  /**
   * @return unique Clock instance
   */
  public static Clock getInstance() {
    if (uniqueInstanceClock == null) {
      uniqueInstanceClock = new Clock();
    }

    return uniqueInstanceClock;
  }

  /**
   * Notify all observers to update with the actual date
   */
  public void tick() {
    setChanged();
    notifyObservers(LocalDateTime.now());
  }

  /**
   * Delete timer created in class Clock constructor
   */
  public void deleteTimer() {
    myTimer.cancel();
  }
}
