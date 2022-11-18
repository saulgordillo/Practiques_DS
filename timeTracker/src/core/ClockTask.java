package core;

import java.util.TimerTask;

public class ClockTask extends TimerTask {
  /**
   * Function to be executed by the Timer, gets the unique Clock instance and calls the function tick of the Clock class
   */
  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
