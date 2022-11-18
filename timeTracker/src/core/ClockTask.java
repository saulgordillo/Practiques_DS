package core;

import java.util.TimerTask;

public class ClockTask extends TimerTask {
  //Updates the clock and calls "update" which calls the observer
  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
