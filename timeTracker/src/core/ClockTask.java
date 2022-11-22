package core;

import java.util.TimerTask;

public class ClockTask extends TimerTask {
  /**
   * Function executed by Timer, gets unique Clock instance and calls function tick of Clock class.
   */

  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
