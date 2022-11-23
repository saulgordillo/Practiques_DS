package core;

import java.util.TimerTask;

/**
 * Class which extends TimerTask and sets the function
 * to execute by the schedule method of Clock class.
 */
public class ClockTask extends TimerTask {
  /**
   * Function executed by Timer, gets unique Clock instance and calls function tick of Clock class.
   */
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
