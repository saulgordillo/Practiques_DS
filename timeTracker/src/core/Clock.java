package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

/**
 * Class which extends Observable and works
 * as a real time clock for our app,
 * implements Singleton pattern.
 */
public class Clock extends Observable {
  static Logger loggerClock = LoggerFactory.getLogger("core.Observable.Clock");

  // Singleton pattern
  private static Clock uniqueInstanceClock = null;
  private final Timer myTimer;

  /**
   * Set a timer to execute run() method in ClockTimer class every 2 seconds (2000 milliseconds).
   */
  private Clock() {
    myTimer = new Timer();
    long period = 2000;
    loggerClock.info("Schedule ClockTask (implementation of TimerTask) at " + period + "ms period");
    myTimer.scheduleAtFixedRate(new ClockTask(), 0, period);
  }

  /**
   * @return Instance of the Clock
   */
  public static Clock getInstance() {
    if (uniqueInstanceClock == null) {
      loggerClock.info("Create unique Clock instance");
      uniqueInstanceClock = new Clock();
    }

    return uniqueInstanceClock;
  }

  /**
   * Notify all observers to update with the actual date.
   */
  public void tick() {
    loggerClock.debug("Notify observers");
    setChanged();
    notifyObservers(LocalDateTime.now());
  }

  /**
   * Delete timer created in class Clock constructor.
   */
  public void deleteTimer() {
    loggerClock.info("Delete (cancel) timer ClockTask");
    myTimer.cancel();
  }
}
