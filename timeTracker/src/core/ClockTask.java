package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class ClockTask extends TimerTask {
	
  static Logger loggerClockTask= LoggerFactory.getLogger("core.TimerTask.ClockTask");
  /**
   * Function to be executed by the Timer, gets the unique Clock instance and calls the function tick of the Clock class
   */
  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
