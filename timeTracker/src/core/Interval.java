package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  static Logger loggerInterval = LoggerFactory.getLogger("core.Observer.Interval");
  //Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private final Task myTask;
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Duration duration;

  /**
   * Constructor to create an Interval with a Task father
   *
   * @param father
   */
  public Interval(Task father) {
    this.myTask = father;
    this.duration = Duration.ofSeconds(0);
  }

  /**
   * @return Duration of the Interval class object
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * Print Interval info (Initial date, Final date and Duration)
   */
  public void printInterval() {
    System.out.println("Interval: " + "                  " + this.initialDate.format(formatter) + "   " + this.finalDate.format(formatter) + "            " + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));

    myTask.printActivity();

  }

  /**
   * Accept Visitor to visit the Interval
   *
   * @param visitor
   */
  public void accept(Visitor visitor) {
    visitor.visitInterval(this);
  }

  /**
   * Updates date when the Observable has changed, in this case when the Clock instance changes
   *
   * @param observable the observable object.
   * @param object     an argument passed to the {@code notifyObservers}
   *                   method.
   */
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = (LocalDateTime) object;
      initialDate = initialDate.minus(2, ChronoUnit.SECONDS);
    }
	loggerInterval.info("Exists an Initial ");
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    myTask.updateDatesAndDuration(initialDate, finalDate);
    this.printInterval();
  }

  /**
   * @return JSONObject containing the info of the Interval class object
   */
  //Create JSONObject
  public JSONObject intervalToJSON() {
    JSONObject interval = new JSONObject();
    interval.put("initialDate", initialDate.format(formatter));
    interval.put("finalDate", finalDate.format(formatter));
    interval.put("task", myTask.name);
    interval.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    return interval;
  }
}
