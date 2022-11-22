package core;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ClockTask extends TimerTask {
	
  static Logger loggerClockTask = LoggerFactory.getLogger("core.TimerTask.ClockTask");
  /**
   * Function executed by Timer, gets unique Clock instance and calls function tick of Clock class.
   */

  @Override
  public void run() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.tick();
  }
}
